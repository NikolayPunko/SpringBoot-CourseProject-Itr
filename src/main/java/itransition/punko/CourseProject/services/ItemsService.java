package itransition.punko.CourseProject.services;

import itransition.punko.CourseProject.models.Item;
import itransition.punko.CourseProject.models.Tag;
import itransition.punko.CourseProject.repositories.ItemsRepository;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class ItemsService {

    private final CollectionsService collectionsService;
    private final ItemsRepository itemsRepository;
    private final TagsService tagsService;
    private final UsersService usersService;
    private final EntityManager entityManager;


    public ItemsService(CollectionsService collectionsService, ItemsRepository itemsRepository, TagsService tagsService, UsersService usersService, EntityManager entityManager) {
        this.collectionsService = collectionsService;
        this.itemsRepository = itemsRepository;
        this.tagsService = tagsService;
        this.usersService = usersService;
        this.entityManager = entityManager;
    }

    public Item createNewItem(int collectionId) {
        return new Item(collectionsService.findOne(collectionId));
    }

    @Transactional
    public void assignTagsAndSaveItem(Item item, String[] tagNames) {
        item.setTags(tagsService.getTagsByNameAndSave(new HashSet<>(Arrays.asList(tagNames))));
        itemsRepository.save(item);
    }

    @Transactional
    public void save(Item item) {
        itemsRepository.save(item);
    }

    public List<Item> findAll() {
        return itemsRepository.findAll();
    }

    public Item findOne(int id) {
        Optional<Item> findItem = itemsRepository.findById(id);
        return findItem.orElse(null);
    }

    public ArrayList<String> getTagsNameByItemId(int id) {
        ArrayList<String> tagsName = new ArrayList<>();
        for (Tag tag : findOne(id).getTags()) {
            tagsName.add(tag.getTag());
        }
        return tagsName;
    }

    public List<Item> findLast5() {
        Optional<List<Item>> items = itemsRepository.findFirst5ByOrderByIdDesc();
        return items.orElse(null);
    }

    @Transactional
    public void update(int id, Item updatedItem, String[] tagNames) {
        Item itemToBeUdated = findOne(id);
        updatedItem.setLikes(itemToBeUdated.getLikes());
        updatedItem.setComments(itemToBeUdated.getComments());
        assignTagsAndSaveItem(updatedItem, tagNames);
    }

    @Transactional
    public void delete(int id) {
        itemsRepository.deleteById(id);
    }

    @Transactional
    public void like(int itemId, int userId) {
        findOne(itemId).getLikes().add(usersService.findById(userId).get());
    }

    @Transactional
    public void dislike(int itemId, int userId) {
        findOne(itemId).getLikes().remove(usersService.findById(userId).get());
    }

    public Page<Item> paginated(int collectionId, int page, String sort) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sort));
        return itemsRepository.findAllByOwnerId(collectionId, pageable);
    }

    private void initializeHibernateSearch() {
        try {
            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private FullTextEntityManager getFullTextEntityManager(){
        initializeHibernateSearch();
        return Search.getFullTextEntityManager(entityManager);
    }

    public List<Item> searchByText(String search){
        FullTextEntityManager fullTextEntityManager = getFullTextEntityManager();
        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Item.class).get();

        search = search.replace("_", " ");
        if(search.length()==0)
            return Collections.emptyList();

        Query query = queryBuilder
                .keyword().fuzzy().withEditDistanceUpTo(2).withPrefixLength(0)
                .onFields("name", "firstStringField", "secondStringField", "thirdStringField", "firstTextField", "secondTextField", "thirdTextField")
                .matching(search)
                .createQuery();

        return searchItems(query, fullTextEntityManager);
    }



    private List<Item> searchItems(Query query, FullTextEntityManager fullTextEntityManager) {
        javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(query, Item.class);

        List<Item> items;
        try {
            items = jpaQuery.getResultList();
        } catch (NoResultException nre) {
            return Collections.emptyList();
        }

        return items;
    }

    public List<Item> findAllByTagId(int tagId){
        return itemsRepository.findByTagsContains(tagsService.findOne(tagId));
    }


}
