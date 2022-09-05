package itransition.punko.CourseProject.services;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import itransition.punko.CourseProject.models.Collection;
import itransition.punko.CourseProject.repositories.CollectionsRepository;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class CollectionsService {

    private final CollectionsRepository collectionsRepository;
    private final UsersService usersService;
    private final Cloudinary cloudinaryConfig;

    @Autowired
    public CollectionsService(CollectionsRepository collectionsRepository, UsersService usersService, Cloudinary cloudinaryConfig) {
        this.collectionsRepository = collectionsRepository;
        this.usersService = usersService;
        this.cloudinaryConfig = cloudinaryConfig;
    }

    public Page<Collection> paginated(int page, int userId) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id"));
        if (userId == 0) {
            return collectionsRepository.findAll(pageable);
        } else {
            return collectionsRepository.findAllByOwnerId(userId, pageable);
        }
    }

    public List<Collection> findAllByUserId(int userId) {
        return collectionsRepository.findByOwnerId(userId);
    }

    public List<Collection> findAll() {
        return collectionsRepository.findAll();
    }

    @Transactional
    public void save(Collection collection) {
        collectionsRepository.save(collection);
    }

    @Transactional
    public void create(Collection collection, MultipartFile file, ArrayList<Integer> list, int userId) {
        collection = assignPhoto(assignFields(collection, list), file);
        collection.setDescription(markdownToHTML(collection.getMarkdown()));
        collection.setOwner(usersService.findById(userId).get());
        save(collection);
    }

    @Transactional
    public void update(Collection updatedCollection, MultipartFile file, ArrayList<Integer> list, boolean check) {
        Collection collection = findOne(updatedCollection.getId());
        updatedCollection = photoUpdateCheck(collection, assignFields(updatedCollection, list), file, check);
        updatedCollection.setOwner(collection.getOwner());
        updatedCollection.setItems(collection.getItems());
        updatedCollection.setDescription(markdownToHTML(updatedCollection.getMarkdown()));
        save(updatedCollection);
    }

    private Collection photoUpdateCheck(Collection oldCollection, Collection updatedCollection, MultipartFile file, boolean check) {
        if (check)
            updatedCollection = assignPhoto(updatedCollection, file);
        else
            updatedCollection.setUrlPhoto(oldCollection.getUrlPhoto());
        return updatedCollection;
    }

    public Collection findOne(int id) {
        Optional<Collection> findCollection = collectionsRepository.findById(id);
        return findCollection.orElse(null);
    }


    private String markdownToHTML(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        return HtmlRenderer.builder().build().render(document);
    }


    private String uploadFile(MultipartFile file) {
        try {
            File uploadedFile = convertMultiPartToFile(file);
            Map uploadResult = cloudinaryConfig.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
            return uploadResult.get("url").toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream outputStream = new FileOutputStream(new File(file.getOriginalFilename()));
        outputStream.write(file.getBytes());
        outputStream.close();
        return convFile;
    }

    private Collection assignFields(Collection collection, ArrayList<Integer> list) {
        int mask = collection.getBitmask();
        for (int x : list) {
            mask |= (1 << x);
        }
        collection.setBitmask(mask);
        return collection;
    }

    public int[] getFields(int collectionId) {
        Collection collection = findOne(collectionId);
        int[] result = new int[15];
        for (int i = 0; i < 15; i++) {
            result[i] = collection.getBitmask() & (1 << i);
        }
        int s = result.length;
        return result;
    }

    private Collection assignPhoto(Collection collection, MultipartFile file) {
        if (file.getOriginalFilename().equals("blob"))
            collection.setUrlPhoto("NULL");
        else
            collection.setUrlPhoto(uploadFile(file));
        return collection;
    }

    @Transactional
    public void delete(int id) {
        collectionsRepository.deleteById(id);
    }

}
