package tech.yump.jobportal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.yump.jobportal.entity.JobSeekerProfile;
import tech.yump.jobportal.entity.RecruiterProfile;
import tech.yump.jobportal.enums.UserType;
import tech.yump.jobportal.repository.JobSeekerProfileRepository;
import tech.yump.jobportal.repository.RecruiterProfileRepository;
import tech.yump.jobportal.repository.UsersRepository;
import tech.yump.jobportal.entity.Users;

import java.util.Date;
import java.util.Optional;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;

    @Autowired
    public UsersService(
            UsersRepository usersRepository,
            JobSeekerProfileRepository jobSeekerProfileRepository,
            RecruiterProfileRepository recruiterProfileRepository) {
        this.usersRepository = usersRepository;
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
    }

    public void addNewUser(Users user) {
        user.setActive(true);
        user.setRegistrationDate(new Date(System.currentTimeMillis()));
        Users savedUser = usersRepository.save(user);
        UserType userTypeId = UserType.fromId(user.getUserTypeId().getUserTypeId());
        if (userTypeId == UserType.JOB_SEEKER) {
            jobSeekerProfileRepository.save(new JobSeekerProfile(savedUser));
        } else if (userTypeId == UserType.RECRUITER) {
            recruiterProfileRepository.save(new RecruiterProfile(savedUser));
        }
    }

    public Optional<Users> getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }
}
