package tech.yump.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.yump.jobportal.entity.UserType;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {
    UserType findByName(String name);
}
