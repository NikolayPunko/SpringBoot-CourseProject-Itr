package itransition.punko.CourseProject.util;

import itransition.punko.CourseProject.models.User;
import itransition.punko.CourseProject.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class UserValidator implements Validator {

    private final UsersService usersService;

    @Autowired
    public UserValidator(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if (usersService.findByUsername(user.getUsername()).isPresent())
            errors.rejectValue("username", "", "User with this login already exists");

        if (usersService.findByEmail(user.getEmail()).isPresent())
            errors.rejectValue("email", "", "User with this email address already exists");
    }
}
