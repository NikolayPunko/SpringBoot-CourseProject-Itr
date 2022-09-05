package itransition.punko.CourseProject.controllers;

import itransition.punko.CourseProject.services.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/search")
public class SearchController {

    private final ItemsService itemsService;

    @Autowired
    public SearchController(ItemsService itemsService) {
        this.itemsService = itemsService;
    }


    @GetMapping()
    public String showPage(Model model, @RequestParam("text") String text) {
        model.addAttribute("text", text);
        return "search/search_page";
    }

    @GetMapping("/get")
    public String getResult(Model model, @RequestParam("text") String text) {
        model.addAttribute("items", itemsService.searchByText(text));
        return "search/search_fragment :: searchList";
    }
}
