package springboot.webtruyen;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import springboot.webtruyen.model.Story;
import springboot.webtruyen.repository.StoryRepository;

@Controller
public class HomeController {
	@Autowired
    private StoryRepository storyRepository;

    @GetMapping("/")
    public String index(Model model) {
    	List<Story> list = storyRepository.findAll();
    	
    	  for (Story story : list) {
    	        System.out.println(">> " + story.getId() + " - " + story.getTitle());
    	    }

    	model.addAttribute("dsTruyen", list);
        model.addAttribute("message", "Chào mừng bạn đến với Web Truyện!");
        return "index"; // trỏ đến file index.html trong templates
    }
}
