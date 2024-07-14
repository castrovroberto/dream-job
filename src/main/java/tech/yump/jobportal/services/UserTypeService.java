package tech.yump.jobportal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.yump.jobportal.entity.UserType;
import tech.yump.jobportal.repository.UserTypeRepository;

import java.util.List;

@Service
public class UserTypeService {

    private final UserTypeRepository usersTypeRepository;

    @Autowired
    public UserTypeService(UserTypeRepository usersTypeRepository) {
        this.usersTypeRepository = usersTypeRepository;
    }

    public List<UserType> getAll() {
        return usersTypeRepository.findAll();
    }

}
