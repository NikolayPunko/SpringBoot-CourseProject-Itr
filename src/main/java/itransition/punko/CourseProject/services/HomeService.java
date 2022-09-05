package itransition.punko.CourseProject.services;

import itransition.punko.CourseProject.models.Collection;
import itransition.punko.CourseProject.models.Item;
import itransition.punko.CourseProject.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeService {

    private final ItemsService itemsService;
    private final CollectionsService collectionsService;
    private final TagsService tagsService;

    @Autowired
    public HomeService(ItemsService itemsService, CollectionsService collectionsService, TagsService tagsService) {
        this.itemsService = itemsService;
        this.collectionsService = collectionsService;
        this.tagsService = tagsService;
    }


    public List<Item> findLastFiveItems() {
        return itemsService.findLast5();
    }

    public List<Collection> findFiveBiggestCollections() {
        return collectionsService.findAll().stream().sorted((o1, o2)->o2.getItems().size()-(o1.getItems().size())).limit(5)
                .collect(Collectors.toList());
    }

    public List<Tag> findAllTags(){
        return tagsService.findAll();
    }
    
}
