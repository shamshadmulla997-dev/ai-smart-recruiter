package ai_smart_recruiter.service;

import ai_smart_recruiter.model.Resume;
import ai_smart_recruiter.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    public Resume save(Resume resume) {
        resume.setUploadedAt(LocalDateTime.now());
        return resumeRepository.save(resume);
    }

    public List<Resume> getAll() {
        return resumeRepository.findAll();
    }

    public Resume findById(Long id) {
        return resumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resume not found: " + id));
    }
}