package itransition.punko.CourseProject.repositories;

import itransition.punko.CourseProject.models.Collection;
import itransition.punko.CourseProject.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollectionsRepository extends JpaRepository<Collection, Integer>, PagingAndSortingRepository<Collection, Integer> {

    List<Collection> findByOwnerId(int id);

    Page<Collection> findAllByOwnerId(int id, Pageable pageable);

    Page<Collection> findAll(Pageable pageable);

}
