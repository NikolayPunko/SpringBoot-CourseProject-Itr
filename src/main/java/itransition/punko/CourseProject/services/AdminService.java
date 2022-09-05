package itransition.punko.CourseProject.services;

import itransition.punko.CourseProject.models.User;
import itransition.punko.CourseProject.models.UserRole;
import itransition.punko.CourseProject.repositories.UsersRepository;
import itransition.punko.CourseProject.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class AdminService {

    private final UsersRepository usersRepository;
    private final SessionUtil sessionUtil;

    @Autowired
    public AdminService(UsersRepository usersRepository, SessionUtil sessionUtil) {
        this.usersRepository = usersRepository;
        this.sessionUtil = sessionUtil;
    }


    public Page<User> findAllWithPagination(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id"));
        return usersRepository.findAll(pageable);
    }


    public List<User> findAll() {
        return usersRepository.findAll(Sort.by("id"));
    }

    @Transactional
    public void delete(int id) {
        sessionUtil.expireUserSessions(usersRepository.findById(id).get().getUsername());
        usersRepository.deleteById(id);
    }

    @Transactional
    public void block(int id) {
        User user = usersRepository.findById(id).get();
        user.setStatus("Block");
        sessionUtil.expireUserSessions(user.getUsername());
    }


    @Transactional
    public void unblock(int id) {
        usersRepository.findById(id).get().setStatus("Unblock");
    }

    @Transactional
    public void setAdmin(int id) {
        User user = usersRepository.findById(id).get();
        user.setUserRole(UserRole.ROLE_ADMIN);
        sessionUtil.expireUserSessions(user.getUsername());
    }

    @Transactional
    public void removeAdmin(int id) {
        User user = usersRepository.findById(id).get();
        user.setUserRole(UserRole.ROLE_USER);
        sessionUtil.expireUserSessions(user.getUsername());
    }


}
