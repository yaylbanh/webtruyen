package springboot.webtruyen.Utils;
import java.text.Normalizer;
import java.util.Locale;

public class Utils {
	 public static String toSlug(String input) {
	        String nowhitespace = input.trim().replaceAll("\\s+", "-");
	        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
	        String slug = normalized.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
	        slug = slug.replaceAll("[^a-zA-Z0-9\\-]", ""); // chỉ giữ lại chữ, số và dấu gạch ngang
	        return slug.toLowerCase(Locale.ROOT);
	    }
}
