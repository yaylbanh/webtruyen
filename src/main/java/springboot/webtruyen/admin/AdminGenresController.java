package springboot.webtruyen.admin;
import springboot.webtruyen.Utils.Utils;
import springboot.webtruyen.model.Genre;
import springboot.webtruyen.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@Controller
@RequestMapping("/admin/genres")
public class AdminGenresController {

    @Autowired
    private GenreRepository itemRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("genres", itemRepository.findAll());
        return "admin/genres_list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Genre());
        return "admin/genre_form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Genre item) throws IOException {
    	item.setSlug(Utils.toSlug(item.getName()));
        itemRepository.save(item);
        return "redirect:/admin/genres";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Genre item = itemRepository.findById(id).orElseThrow();
        model.addAttribute("item", item);
        return "admin/genre_form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        itemRepository.deleteById(id);
        return "redirect:/admin/genres";
    }
}