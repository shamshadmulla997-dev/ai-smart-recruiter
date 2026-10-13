package ai_smart_recruiter.controller;

import ai_smart_recruiter.ai.AiMatchingService;
import ai_smart_recruiter.model.Resume;
import ai_smart_recruiter.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class AnalyticsController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private AiMatchingService aiMatchingService;

    @GetMapping("/analytics")
    public String analytics(Model model) {
        List<Resume> resumes = resumeService.getAll();

        Map<String, Integer> skillFreq = aiMatchingService.getSkillFrequency(resumes);
        Map<String, Integer> topRoles = aiMatchingService.getTopMatchedRoles(resumes);
        double avgExp = aiMatchingService.getAverageExperience(resumes);

        model.addAttribute("totalResumes", resumes.size());
        model.addAttribute("avgExperience", Math.round(avgExp * 10.0) / 10.0);
        model.addAttribute("skillFreq", skillFreq);
        model.addAttribute("topRoles", topRoles);
        model.addAttribute("resumes", resumes);

        return "analytics";
    }
}