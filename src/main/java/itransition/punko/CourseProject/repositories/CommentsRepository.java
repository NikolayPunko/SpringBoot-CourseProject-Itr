package itransition.punko.CourseProject.repositories;

import itransition.punko.CourseProject.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByOwnerId(int id);

}
