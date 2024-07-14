package tech.yump.jobportal.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.yump.jobportal.entity.JobSeekerProfile;
import tech.yump.jobportal.entity.RecruiterProfile;
import tech.yump.jobportal.enums.UserTypeEnum;
import tech.yump.jobportal.repository.JobSeekerProfileRepository;
import tech.yump.jobportal.repository.RecruiterProfileRepository;
import tech.yump.jobportal.repository.UserRepository;
import tech.yump.jobportal.entity.User;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository usersRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(
            UserRepository usersRepository,
            JobSeekerProfileRepository jobSeekerProfileRepository,
            RecruiterProfileRepository recruiterProfileRepository,
            PasswordEncoder passwordEncoder) {
        logger.debug("UserService constructor");
        this.usersRepository = usersRepository;
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User addNewUser(User user) {
        logger.debug("addNewUser");
        user.setActive(true);
        user.setRegistrationDate(new Date(System.currentTimeMillis()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = usersRepository.save(user);
        UserTypeEnum userTypeEnumId = UserTypeEnum.fromId(user.getUserType().getId());
        if (userTypeEnumId == UserTypeEnum.JOB_SEEKER) {
            logger.debug("Adding job seeker profile");
            jobSeekerProfileRepository.save(new JobSeekerProfile(savedUser));
        } else if (userTypeEnumId == UserTypeEnum.RECRUITER) {
            logger.debug("Adding recruiter profile");
            recruiterProfileRepository.save(new RecruiterProfile(savedUser));
        }
        return savedUser;
    }

    public Optional<User> getUserByEmail(String email) {
        logger.debug("getUserByEmail: {}", email);
        return usersRepository.findByEmail(email);
    }

    public Object getCurrentUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentPrincipalName = authentication.getName();
            User user = usersRepository.findByEmail(currentPrincipalName).orElseThrow(() -> new IllegalArgumentException("User not found"));
            Long userId = user.getUserId();
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(UserTypeEnum.RECRUITER.name()))) {
                return recruiterProfileRepository.findById(userId).orElse(new RecruiterProfile());
            } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(UserTypeEnum.JOB_SEEKER.name()))) {
                return jobSeekerProfileRepository.findById(userId).orElse(new JobSeekerProfile());
            } else {
                throw(new IllegalArgumentException("User not found"));
            }
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }
}
