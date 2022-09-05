package itransition.punko.CourseProject.controllers;

import itransition.punko.CourseProject.models.Comment;
import itransition.punko.CourseProject.services.CommentsService;
import itransition.punko.CourseProject.services.UsersDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/comment")
public class CommentController {

    private final CommentsService commentsService;

    @Autowired
    public CommentController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @GetMapping("item/{id}")
    public String index(Model model, @PathVariable("id") int id) {
        model.addAttribute("comments", commentsService.findByItemId(id));
        model.addAttribute("item_id", id);
        return "comments/result_fragment :: commentList";
    }

    @PostMapping("/create")
    public String save(@ModelAttribute("comment") Comment comment, @RequestParam(value = "item_id") int itemId,
                       @RequestParam(value = "user_id") int userId) {
        commentsService.save(comment, itemId, userId);
        return "redirect:/item/show/"+itemId;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id, @RequestParam(value = "item_id") int itemId) {
        commentsService.delete(id);
        return "redirect:/item/show/"+itemId;
    }



}
