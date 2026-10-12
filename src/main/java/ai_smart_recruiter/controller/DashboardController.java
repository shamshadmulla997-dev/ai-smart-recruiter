package ai_smart_recruiter.controller;

import ai_smart_recruiter.ai.AiMatchingService;
import ai_smart_recruiter.model.Resume;
import ai_smart_recruiter.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private AiMatchingService aiMatchingService;

    /** All resumes as a list */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Resume> all = resumeService.getAll();
        model.addAttribute("resumes", all);
        model.addAttribute("total", all.size());
        return "dashboard";
    }

    /** Career guide for one resume */
    @GetMapping("/career-guide/{id}")
    public String careerGuide(@PathVariable Long id, Model model) {
        Resume resume = resumeService.findById(id);

        model.addAttribute("resume", resume);
        model.addAttribute("resumeSkills", aiMatchingService.getResumeSkills(resume));
        model.addAttribute("topRoles", aiMatchingService.getBestRolesForResume(resume, 5));

        return "career-guide";
    }
}