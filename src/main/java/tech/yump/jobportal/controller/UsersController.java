package tech.yump.jobportal.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
        Optional<User> optionalUser = userService.getUserByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            model.addAttribute("error", "Email already exists. Try to login or register with another email.");
            List<UserType> userTypes = usersTypeService.getAll();
            model.addAttribute("getAllTypes", userTypes);
            model.addAttribute("user", new User());
            return "register";
        }
        logger.debug("User registration successful");
        userService.addNewUser(user);
        return "redirect:/dashboard/";
    }

    @GetMapping("/login")
    public String login() {
        logger.debug("Login page requested");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Logout page requested");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
             new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }
}
