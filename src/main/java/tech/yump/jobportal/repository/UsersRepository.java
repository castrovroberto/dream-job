package tech.yump.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.yump.jobportal.entity.Users;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
}
