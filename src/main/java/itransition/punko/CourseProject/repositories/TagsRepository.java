package itransition.punko.CourseProject.repositories;

import itransition.punko.CourseProject.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagsRepository extends JpaRepository<Tag,Integer> {

    Optional<Tag> findByTag(String tag);

}
