package itransition.punko.CourseProject.controllers;

import itransition.punko.CourseProject.models.User;
import itransition.punko.CourseProject.services.UsersDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@ControllerAdvice
@SessionAttributes({"auth_user", "role"})
public class UserControllerAdvice {

    private final UsersDetailsService usersDetailsService;

    @Autowired
    public UserControllerAdvice(UsersDetailsService usersDetailsService) {
        this.usersDetailsService = usersDetailsService;
    }

    @ModelAttribute("auth_user")
    public User getUser() {
        return usersDetailsService.loggedUser();
    }

    @ModelAttribute("role")
    public String getRole() {
        if(usersDetailsService.loggedUser()==null)
            return "null";
        return usersDetailsService.loggedUser().getUserRole().getLabel();
    }


}
