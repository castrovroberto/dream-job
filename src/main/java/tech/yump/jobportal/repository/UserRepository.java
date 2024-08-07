package tech.yump.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.yump.jobportal.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
