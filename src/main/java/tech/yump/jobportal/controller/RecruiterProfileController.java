package tech.yump.jobportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.yump.jobportal.entity.RecruiterProfile;
import tech.yump.jobportal.entity.User;
import tech.yump.jobportal.repository.UserRepository;
import tech.yump.jobportal.services.RecruiterProfileService;

import java.util.Optional;

@Controller
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {

    private final UserRepository userRepository;

    private final RecruiterProfileService recruiterProfileService;

    @Autowired
    public RecruiterProfileController(
            UserRepository userRepository,
            RecruiterProfileService recruiterProfileService) {
        this.userRepository = userRepository;
        this.recruiterProfileService = recruiterProfileService;
    }

    @GetMapping("/")
    public String getRecruiterProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            User user = userRepository.findByEmail(currentUserName)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + currentUserName));

            Optional<RecruiterProfile> recruiterProfile = recruiterProfileService.getRecruiterProfileById(user.getUserId());

            model.addAttribute("user", user);
            model.addAttribute("username", currentUserName);
            recruiterProfile.ifPresent(profile -> model.addAttribute("recruiterProfile", profile));
        }
        return "recruiter-profile";
    }

}
