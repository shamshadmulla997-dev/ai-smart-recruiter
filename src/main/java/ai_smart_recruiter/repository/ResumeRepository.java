package ai_smart_recruiter.repository;

import ai_smart_recruiter.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
}