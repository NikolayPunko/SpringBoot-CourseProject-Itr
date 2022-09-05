package itransition.punko.CourseProject.controllers;

import itransition.punko.CourseProject.services.HomeService;
import itransition.punko.CourseProject.services.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class HomeController {

    private final HomeService homeService;


    @Autowired
    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping()
    public String listPeople(Model model) {
        model.addAttribute("items", homeService.findLastFiveItems());
        model.addAttribute("collections", homeService.findFiveBiggestCollections());
        model.addAttribute("tags", homeService.findAllTags());
        return "home";
    }

}
