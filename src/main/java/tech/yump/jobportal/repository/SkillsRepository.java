package tech.yump.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.yump.jobportal.entity.Skills;

public interface SkillsRepository extends JpaRepository<Skills, Long> {
}
