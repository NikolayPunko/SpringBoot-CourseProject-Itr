package itransition.punko.CourseProject.repositories;

import itransition.punko.CourseProject.models.Item;
import itransition.punko.CourseProject.models.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemsRepository extends JpaRepository<Item,Integer>,PagingAndSortingRepository<Item, Integer>  {

    Optional<List<Item>> findFirst5ByOrderByIdDesc();

    Page<Item> findAllByOwnerId(int id, Pageable pageable);

    List<Item> findByTagsContains(Tag tag);


}
