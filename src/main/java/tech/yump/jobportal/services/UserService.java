package tech.yump.jobportal.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.yump.jobportal.entity.JobSeekerProfile;
import tech.yump.jobportal.entity.RecruiterProfile;
import tech.yump.jobportal.enums.UserType;
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

    public void addNewUser(User user) {
        logger.debug("addNewUser");
        user.setActive(true);
        user.setRegistrationDate(new Date(System.currentTimeMillis()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = usersRepository.save(user);
        UserType userTypeId = UserType.fromId(user.getUserTypeId().getUserTypeId());
        if (userTypeId == UserType.JOB_SEEKER) {
            logger.debug("Adding job seeker profile");
            jobSeekerProfileRepository.save(new JobSeekerProfile(savedUser));
        } else if (userTypeId == UserType.RECRUITER) {
            logger.debug("Adding recruiter profile");
            recruiterProfileRepository.save(new RecruiterProfile(savedUser));
        }
    }

    public Optional<User> getUserByEmail(String email) {
        logger.debug("getUserByEmail: {}", email);
        return usersRepository.findByEmail(email);
    }
}
