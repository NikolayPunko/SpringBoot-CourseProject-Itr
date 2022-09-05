package itransition.punko.CourseProject.controllers;

import itransition.punko.CourseProject.models.Comment;
import itransition.punko.CourseProject.models.Item;
import itransition.punko.CourseProject.services.CollectionsService;
import itransition.punko.CourseProject.services.ItemsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/item")
public class ItemController {

    private final ItemsService itemsService;
    private final CollectionsService collectionsService;

    public ItemController(ItemsService itemsService, CollectionsService collectionsService) {
        this.itemsService = itemsService;
        this.collectionsService = collectionsService;
    }

    @GetMapping("/create")
    public String create(Model model, @RequestParam(value = "collection_id") int collectionId,
                         @RequestParam(value = "user_id") int userId) {
        model.addAttribute("item", itemsService.createNewItem(collectionId));
        model.addAttribute("fields", collectionsService.getFields(collectionId));
        model.addAttribute("user_id", userId);
        return "items/new_item";
    }

    @PostMapping("/create")
    public String save(@ModelAttribute("item") Item item, String[] tagNames, @RequestParam(value = "collection_id") int collectionId
            , @RequestParam(value = "user_id") int userId) {
        itemsService.assignTagsAndSaveItem(item,tagNames);
        return "redirect:/collection/show/" + collectionId+"?user_id="+userId;
    }

    @GetMapping("/show/{id}")
    public String showByCollection(Model model, @PathVariable("id") int id) {

        model.addAttribute("fields", collectionsService.getFields(itemsService.findOne(id).getOwner().getId()));
        model.addAttribute("item",itemsService.findOne(id));
        model.addAttribute("comment",new Comment());

        return "items/show_item";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id, @RequestParam(value = "collection_id") int collectionId) {
        model.addAttribute("fields", collectionsService.getFields(collectionId));
        model.addAttribute("tagName", itemsService.getTagsNameByItemId(id));
        model.addAttribute("item", itemsService.findOne(id));
        return "items/edit_item";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("item") Item item, @PathVariable("id") int id, String[] tagNames) {
        itemsService.update(id, item, tagNames);
        return "redirect:/collection/show/" + item.getOwner().getId()+"?user_id="+item.getOwner().getOwner().getId();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id, @RequestParam(value = "collection_id") int collectionId
            , @RequestParam(value = "user_id") int userId) {
        itemsService.delete(id);
        return "redirect:/collection/show/" + collectionId+"?user_id="+userId;
    }

    @GetMapping("/tag/{id}")
    public String create(Model model, @PathVariable("id") int tagId) {
        model.addAttribute("items", itemsService.findAllByTagId(tagId));
        return "items/items_by_tag";
    }

}
