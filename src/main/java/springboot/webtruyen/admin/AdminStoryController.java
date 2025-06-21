package springboot.webtruyen.admin;
import springboot.webtruyen.Utils.Utils;
import springboot.webtruyen.model.Story;
import springboot.webtruyen.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/admin/stories")
public class AdminStoryController {

    @Autowired
    private StoryRepository storyRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("stories", storyRepository.findAll());
        return "admin/story_list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("story", new Story());
        return "admin/story_form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Story story,
                       @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
    	story.setSlug(Utils.toSlug(story.getTitle()));
    	
    	
        // Lấy lại bản ghi cũ nếu đang cập nhật
        Story existingStory = null;
        if (story.getId() != null) {
            existingStory = storyRepository.findById(story.getId()).orElse(null);
        }

        if (!imageFile.isEmpty()) {
            // Nếu có ảnh cũ, xóa nó
            if (existingStory != null && existingStory.getCoverImage() != null) {
                String oldFileName = existingStory.getCoverImage().replace("/images/", "");
                Path oldPath = Paths.get("src/main/resources/static/images", oldFileName);
                try {
                    Files.deleteIfExists(oldPath);
                } catch (IOException e) {
                    System.err.println("Không thể xóa ảnh cũ: " + e.getMessage());
                }
            }

            // Lưu ảnh mới
            String fileName = story.getSlug() + "_metruyen_" + imageFile.getOriginalFilename();
            Path uploadPath = Paths.get("src/main/resources/static/images");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Files.write(uploadPath.resolve(fileName), imageFile.getBytes());

            story.setCoverImage("/images/" + fileName);
        } else if (existingStory != null) {
            // Nếu không upload ảnh mới, giữ nguyên ảnh cũ
            story.setCoverImage(existingStory.getCoverImage());
        }
        
        storyRepository.save(story);
        return "redirect:/admin/stories";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Story story = storyRepository.findById(id).orElseThrow();
        model.addAttribute("story", story);
        return "admin/story_form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        storyRepository.deleteById(id);
        return "redirect:/admin/stories";
    }
}