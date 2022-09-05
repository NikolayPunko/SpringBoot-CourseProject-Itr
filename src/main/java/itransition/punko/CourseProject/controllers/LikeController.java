package itransition.punko.CourseProject.controllers;

import itransition.punko.CourseProject.services.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/like")
public class LikeController {

    private final ItemsService itemsService;

    @Autowired
    public LikeController(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @GetMapping("item/{id}")
    public String index(Model model, @PathVariable("id") int id) {
        model.addAttribute("likes", itemsService.findOne(id).getLikes());
        model.addAttribute("item_id", id);
        return "likes/like_fragment :: likeList";
    }

    @PatchMapping("like/{id}")
    public String like(@PathVariable("id") int itemId, @RequestParam(value = "user_id") int userId) {
        itemsService.like(itemId,userId);
        return "redirect:/item/show/"+itemId;
    }

    @PatchMapping("dislike/{id}")
    public String dislike(@PathVariable("id") int itemId, @RequestParam(value = "user_id") int userId) {
        itemsService.dislike(itemId,userId);
        return "redirect:/item/show/"+itemId;
    }

}
