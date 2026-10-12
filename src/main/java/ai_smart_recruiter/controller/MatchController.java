package ai_smart_recruiter.controller;

import ai_smart_recruiter.ai.AiMatchingService;
import ai_smart_recruiter.model.Resume;
import ai_smart_recruiter.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class MatchController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private AiMatchingService aiMatchingService;

    @GetMapping("/match")
    public String matchPage(Model model) {
        model.addAttribute("roles", aiMatchingService.getAllRoles());
        return "match";
    }

    @PostMapping("/match")
    public String matchResults(@RequestParam("jobRole") String jobRole,
                               Model model) {

        List<String> requiredSkills = aiMatchingService.getSkillsForRole(jobRole);

        // If role not found
        if (requiredSkills.isEmpty()) {
            model.addAttribute("error",
                "❌ Role '" + jobRole + "' not found. Try: Java Developer, Frontend Developer, Data Scientist, etc.");
            model.addAttribute("roles", aiMatchingService.getAllRoles());
            return "match";
        }

        List<Resume> allResumes = resumeService.getAll();
        List<Map<String, Object>> ranked = new ArrayList<>();

        for (Resume r : allResumes) {
            double score = aiMatchingService.matchScore(r, requiredSkills);

            Map<String, Object> row = new HashMap<>();
            row.put("resume", r);
            row.put("score", score);
            row.put("verdict", aiMatchingService.verdict(score));
            row.put("matchedSkills", aiMatchingService.matchedSkills(r, requiredSkills));
            row.put("missingSkills", aiMatchingService.missingSkills(r, requiredSkills));
            row.put("missingSkillsWithLinks", aiMatchingService.missingSkillsWithLinks(r, requiredSkills));
            ranked.add(row);
        }

        ranked.sort((a, b) -> Double.compare((Double) b.get("score"), (Double) a.get("score")));

        model.addAttribute("jobRole", jobRole);
        model.addAttribute("requiredSkills", requiredSkills);
        model.addAttribute("ranked", ranked);
        model.addAttribute("total", allResumes.size());

        return "results";
    }
}