package tech.yump.jobportal.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import tech.yump.jobportal.entity.Users;
import tech.yump.jobportal.entity.UsersType;
import tech.yump.jobportal.services.UsersService;
import tech.yump.jobportal.services.UsersTypeService;

import java.util.List;
import java.util.Optional;

@Controller
public class UsersController {

    private final UsersTypeService usersTypeService;
    private final UsersService usersService;

    @Autowired
    public UsersController(
            UsersTypeService usersTypeService,
            UsersService usersService) {
        this.usersTypeService = usersTypeService;
        this.usersService = usersService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        List<UsersType> usersType = usersTypeService.getAll();
        model.addAttribute("getAllTypes", usersType);
        model.addAttribute("user", new Users());
        return "register";
    }

    @PostMapping("/register/new")
    public String userRegistration(@Valid Users user, Model model) {
        Optional<Users> optionalUsers = usersService.getUserByEmail(user.getEmail());
        if (optionalUsers.isPresent()) {
            List<UsersType> usersType = usersTypeService.getAll();
            model.addAttribute("getAllTypes", usersType);
            model.addAttribute("user", new Users());
            model.addAttribute("error", "Email already exists. Try to login or register with another email.");
            return "register";
        }
        usersService.addNewUser(user);
        return "dashboard";
    }
}
