package tech.yump.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.yump.jobportal.entity.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
