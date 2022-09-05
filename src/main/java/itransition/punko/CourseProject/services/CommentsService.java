package itransition.punko.CourseProject.services;

import itransition.punko.CourseProject.models.Comment;
import itransition.punko.CourseProject.repositories.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CommentsService {

    private final CommentsRepository commentsRepository;
    private final ItemsService itemsService;
    private final UsersService usersService;

    @Autowired
    public CommentsService(CommentsRepository commentsRepository, ItemsService itemsService, UsersService usersService) {
        this.commentsRepository = commentsRepository;
        this.itemsService = itemsService;
        this.usersService = usersService;
    }

    public List<Comment> findByItemId(int id){
        List<Comment> comments = commentsRepository.findByOwnerId(id);
        System.out.println(comments);
        return commentsRepository.findByOwnerId(id);
    }

    @Transactional
    public void save(Comment comment, int itemId, int userId) {
        comment.setOwner(itemsService.findOne(itemId));
        comment.setUser(usersService.findById(userId).get());
        commentsRepository.save(comment);
    }

    @Transactional
    public void delete(int id) {
        commentsRepository.deleteById(id);
    }

    public Comment findOne(int id) {
        Optional<Comment> findComment = commentsRepository.findById(id);
        return findComment.orElse(null);
    }

}
