package tech.yump.jobportal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.yump.jobportal.entity.RecruiterProfile;
import tech.yump.jobportal.repository.RecruiterProfileRepository;

import java.util.Optional;

@Service
public class RecruiterProfileService {

    private final RecruiterProfileRepository recruiterProfileRepository;

    @Autowired
    public RecruiterProfileService(RecruiterProfileRepository recruiterProfileRepository) {
        this.recruiterProfileRepository = recruiterProfileRepository;
    }

    public Optional<RecruiterProfile> getRecruiterProfileById(Long id) {
        return recruiterProfileRepository.findById(id);
    }
}
