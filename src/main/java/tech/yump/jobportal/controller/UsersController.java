package tech.yump.jobportal.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import tech.yump.jobportal.entity.User;
import tech.yump.jobportal.entity.UserType;
import tech.yump.jobportal.services.UserService;
import tech.yump.jobportal.services.UserTypeService;

import java.util.List;
import java.util.Optional;

@Controller
public class UsersController {

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    private final UserTypeService usersTypeService;
    private final UserService userService;

    @Autowired
    public UsersController(
            UserTypeService usersTypeService,
            UserService userService) {
        logger.debug("Initializing UsersController");
        this.usersTypeService = usersTypeService;
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        logger.debug("Register page requested");
        List<UserType> userType = usersTypeService.getAll();
        model.addAttribute("getAllTypes", userType);
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register/new")
    public String userRegistration(@Valid User user, Model model) {
        logger.debug("User registration requested");
        Optional<User> optionalUsers = userService.getUserByEmail(user.getEmail());
        if (optionalUsers.isPresent()) {
            logger.debug("Email already exists. Try to login or register with another email.");
            List<UserType> userType = usersTypeService.getAll();
            model.addAttribute("getAllTypes", userType);
            model.addAttribute("user", new User());
            model.addAttribute("error", "Email already exists. Try to login or register with another email.");
            return "register";
        }
        logger.debug("User registration successful");
        userService.addNewUser(user);
        return "dashboard";
    }
}
