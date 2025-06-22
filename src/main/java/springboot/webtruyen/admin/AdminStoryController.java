package springboot.webtruyen.admin;
import springboot.webtruyen.Utils.Utils;
import springboot.webtruyen.model.Genre;
import springboot.webtruyen.model.Story;
import springboot.webtruyen.repository.ChapterRepository;
import springboot.webtruyen.repository.GenreRepository;
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
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
@Controller
@RequestMapping("/admin/stories")
public class AdminStoryController {

    @Autowired
    private StoryRepository storyRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private ChapterRepository chapterRepository;

    
    @GetMapping
    public String listStories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Story> storyPage = storyRepository.findAll(pageable);

        // Đếm số chương cho từng story
        List<Story> stories = storyPage.getContent();
        for (Story story : stories) {
            long count = chapterRepository.countByStoryId(story.getId());
            story.setChapterCount(count);
        }

        model.addAttribute("storyPage", storyPage);
        model.addAttribute("stories", stories);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", storyPage.getTotalPages());
        return "admin/story_list";
    }

    
//    @GetMapping
//    public String list(Model model) {
//    	 List<Story> stories = storyRepository.findAll();
//
//	    for (Story story : stories) {
//	        long count = chapterRepository.countByStoryId(story.getId());
//	        story.setChapterCount(count);
//	    }
//
//	    model.addAttribute("stories", stories);
//        return "admin/story_list";
//    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("story", new Story());
        model.addAttribute("genres", genreRepository.findAll());
        return "admin/story_form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Story story,
                       @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                       @RequestParam(value = "genreIds", required = false) List<Long> genreIds) throws IOException {

        story.setSlug(Utils.toSlug(story.getTitle()));

        // Lấy bản ghi cũ (nếu đang sửa)
        Story existingStory = null;
        if (story.getId() != null) {
            existingStory = storyRepository.findById(story.getId()).orElse(null);
        }

        // Xử lý ảnh
        if (imageFile != null && !imageFile.isEmpty()) {
            if (existingStory != null && existingStory.getCoverImage() != null) {
                String oldFileName = existingStory.getCoverImage().replace("/images/", "");
                Path oldPath = Paths.get("src/main/resources/static/images", oldFileName);
                Files.deleteIfExists(oldPath);
            }

            String fileName = story.getSlug() + "_metruyen_" + imageFile.getOriginalFilename();
            Path uploadPath = Paths.get("src/main/resources/static/images");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Files.write(uploadPath.resolve(fileName), imageFile.getBytes());
            story.setCoverImage("/images/" + fileName);
        } else if (existingStory != null) {
            story.setCoverImage(existingStory.getCoverImage());
        }

        // Gán thể loại (checkbox)
        if (genreIds != null && !genreIds.isEmpty()) {
            List<Genre> genres = genreRepository.findAllById(genreIds);
            story.setGenres(genres);
        } else {
            //story.setGenres(null);
        }

        storyRepository.save(story);
        return "redirect:/admin/stories";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Story story = storyRepository.findById(id).orElseThrow();
        model.addAttribute("genres", genreRepository.findAll());
        model.addAttribute("story", story);
        return "admin/story_form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        storyRepository.deleteById(id);
        return "redirect:/admin/stories";
    }
}