package tech.yump.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.yump.jobportal.entity.UsersType;

public interface UsersTypeRepository extends JpaRepository<UsersType, Long> {
    UsersType findByUserTypeName(String userTypeName);
}
