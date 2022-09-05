package itransition.punko.CourseProject.services;

import itransition.punko.CourseProject.models.Tag;
import itransition.punko.CourseProject.repositories.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class TagsService {

    private final TagsRepository tagsRepository;

    @Autowired
    public TagsService(TagsRepository tagsRepository) {
        this.tagsRepository = tagsRepository;
    }

    public Tag findOne(int id) {
        Optional<Tag> findTag = tagsRepository.findById(id);
        return findTag.orElse(null);
    }

    public List<Tag> findAll() {
        return tagsRepository.findAll(Sort.by("id"));
    }


    public Tag findOneByTag(String tagName) {
        Optional<Tag> findTag = tagsRepository.findByTag(tagName);
        return findTag.orElse(null);
    }

    @Transactional
    public void save(Tag tag) {
        tagsRepository.save(tag);
    }

    @Transactional
    public Set<Tag> getTagsByNameAndSave(Set<String> tagNames) {
        Set<Tag> tags = new HashSet<>();
        Tag tag;
        for (String tagName: tagNames) {
            if ((tag = findOneByTag(tagName))!=null) {
                tags.add(tag);
            } else {
                save(new Tag(tagName));
                tags.add(findOneByTag(tagName));
            }
        }
        return tags;
    }



}
