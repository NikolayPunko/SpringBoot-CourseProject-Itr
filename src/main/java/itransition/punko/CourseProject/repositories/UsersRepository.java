package itransition.punko.CourseProject.repositories;

import itransition.punko.CourseProject.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer>, PagingAndSortingRepository<User, Integer> {


    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findById(int id);

    Page<User> findAll(Pageable pageable);

}
