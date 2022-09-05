package itransition.punko.CourseProject.controllers;

import itransition.punko.CourseProject.models.Collection;
import itransition.punko.CourseProject.models.Item;
import itransition.punko.CourseProject.models.Topic;
import itransition.punko.CourseProject.services.CollectionsService;
import itransition.punko.CourseProject.services.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/collection")
public class CollectionController {

    private final CollectionsService collectionsService;
    private final ItemsService itemsService;

    @Autowired
    public CollectionController(CollectionsService collectionsService, ItemsService itemsService) {
        this.collectionsService = collectionsService;
        this.itemsService = itemsService;
    }

    @GetMapping("/create")
    public String newCollection(Model model, @RequestParam(value = "user_id") int userId) {
        model.addAttribute("topics", Topic.values());
        model.addAttribute("collection", new Collection());
        model.addAttribute("user_id", userId);
        return "collections/new_collection";
    }

    @GetMapping()
    public String showByUser(Model model,
                             @RequestParam(value = "page", defaultValue = "1") int page,
                             @RequestParam(value = "user_id", defaultValue = "0") int userId) {

        Page<Collection> personPage = collectionsService.paginated(page-1, userId);

        model.addAttribute("collectionsPage", personPage);
        model.addAttribute("pageNumbers", IntStream.rangeClosed(1, personPage.getTotalPages()).boxed().collect(Collectors.toList()));
        model.addAttribute("user_id", userId);

        return "collections/my_collections";
    }

    @GetMapping("/show/{id}")
    public String showCollection(Model model,
                                 @RequestParam(value = "page", defaultValue = "1") int page,
                                 @RequestParam(value = "sort", defaultValue = "id") String sort,
                                 @PathVariable("id") int id, @RequestParam(value = "user_id", defaultValue = "0") int userId) {

        model.addAttribute("collection", collectionsService.findOne(id));
        model.addAttribute("fields", collectionsService.getFields(id));

        Page<Item> itemPage = itemsService.paginated(id, page-1, sort);
        model.addAttribute("page", itemPage);
        model.addAttribute("pageNumbers", IntStream.rangeClosed(1, itemPage.getTotalPages()).boxed().collect(Collectors.toList()));

        model.addAttribute("sort", sort);
        model.addAttribute("user_id", userId);
        return "collections/show_collection";
    }


    @PostMapping("/create")
    public String create(@RequestParam("file[0]") MultipartFile file, @RequestParam(value = "list", required = false) ArrayList<Integer> list,
                         @ModelAttribute("collection") Collection collection, @RequestParam(value = "user_id") int userId) {
        collectionsService.create(collection, file, list, userId);
        return "redirect:/collection?user_id=" + userId;
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("topics", Topic.values());
        model.addAttribute("collection", collectionsService.findOne(id));
        model.addAttribute("fields", collectionsService.getFields(id));

        return "collections/edit_collection";
    }

    @PostMapping("update/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("collection") Collection collection,
                         @RequestParam("file[0]") MultipartFile file, @RequestParam(value = "list") ArrayList<Integer> list,
                         @RequestParam(value = "updateCheck") boolean check) {
        collectionsService.update(collection, file, list, check);
        return "redirect:/collection";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id, @RequestParam(value = "user_id") int userId) {
        collectionsService.delete(id);
        return "redirect:/collection?user_id=" + userId;
    }


}
