package springboot.webtruyen.Utils;
import java.text.Normalizer;
import java.util.Locale;

public class Utils {
	public static String toSlug(String input) {
	    if (input == null) return "";

	    // B1: Thay thế đ và Đ trước khi chuẩn hóa
	    String processed = input.replaceAll("đ", "d").replaceAll("Đ", "D");

	    // B2: Chuẩn hóa tiếng Việt
	    String nowhitespace = processed.trim().replaceAll("\\s+", "-");
	    String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);

	    // B3: Loại bỏ dấu
	    String slug = normalized.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

	    // B4: Chỉ giữ chữ, số, dấu gạch ngang
	    slug = slug.replaceAll("[^a-zA-Z0-9\\-]", "");

	    return slug.toLowerCase(Locale.ROOT);
	}
}
