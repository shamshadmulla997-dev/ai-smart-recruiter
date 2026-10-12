package ai_smart_recruiter.controller;

import ai_smart_recruiter.model.Resume;
import ai_smart_recruiter.service.PdfService;
import ai_smart_recruiter.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PageController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private PdfService pdfService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

    @PostMapping("/submitResume")
    public String submitResume(@ModelAttribute Resume resume,
                               @RequestParam(value = "resumeFile", required = false)
                               MultipartFile resumeFile) {

        // If PDF uploaded, extract text from it
        if (resumeFile != null && !resumeFile.isEmpty()) {
            String pdfText = pdfService.extractText(resumeFile);
            resume.setRawText(pdfText);
        }

        // If still no text, reject
        if (resume.getRawText() == null || resume.getRawText().trim().isEmpty()) {
            return "redirect:/upload?error=nocontent";
        }

        resumeService.save(resume);
        return "redirect:/success";
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }
    @GetMapping("/about")
    public String about() {
        return "about";
    }
}