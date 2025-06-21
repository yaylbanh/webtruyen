package springboot.webtruyen.admin;
import springboot.webtruyen.Utils.Utils;
import springboot.webtruyen.model.Chapter;
import springboot.webtruyen.model.Story;
import springboot.webtruyen.repository.ChapterRepository;
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

@Controller
@RequestMapping("/admin/chapters")
public class AdminChapterController {@Autowired
    private ChapterRepository chapterRepo;

    @Autowired
    private StoryRepository storyRepo;

    @GetMapping("/story/{storyId}")
    public String list(@PathVariable Long storyId, Model model) {
        List<Chapter> chapters = chapterRepo.findByStoryIdOrderByChapterNumberAsc(storyId);
        model.addAttribute("chapters", chapters);
        model.addAttribute("story", storyRepo.findById(storyId).orElseThrow());
        return "admin/chapter_list";
    }

    @GetMapping("/story/{storyId}/add")
    public String add(@PathVariable Long storyId, Model model) {
        Chapter chapter = new Chapter();
        Story story = storyRepo.findById(storyId).orElseThrow();
        chapter.setStory(story);
        model.addAttribute("chapter", chapter);
        model.addAttribute("story", chapter);
        return "admin/chapter_form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Chapter chapter) {
        chapterRepo.save(chapter);
        return "redirect:/admin/chapters/story/" + chapter.getStory().getId();
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Chapter chapter = chapterRepo.findById(id).orElseThrow();
        model.addAttribute("chapter", chapter);
        return "admin/chapter_form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        Chapter chapter = chapterRepo.findById(id).orElseThrow();
        Long storyId = chapter.getStory().getId();
        chapterRepo.deleteById(id);
        return "redirect:/admin/chapters/story/" + storyId;
    }
    
    @GetMapping
    public String redirectToStoryList() {
        // Tạm thời chuyển về trang danh sách truyện hoặc 404 tuỳ ý bạn
        return "redirect:/admin/stories";
    }
}