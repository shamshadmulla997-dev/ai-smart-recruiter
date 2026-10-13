package ai_smart_recruiter.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ChatbotController {

    @PostMapping("/api/chat")
    public Map<String, String> chat(@RequestBody Map<String, String> body) {
        String message = body.getOrDefault("message", "").toLowerCase().trim();
        String reply = getReply(message);

        Map<String, String> response = new HashMap<>();
        response.put("reply", reply);
        return response;
    }

    private String getReply(String msg) {
        if (msg.isEmpty()) return "Please type a question 🙂";

        // Greetings
        if (msg.matches(".*\\b(hi|hello|hey|namaste)\\b.*"))
            return "Hi there! 👋 I'm your AI career assistant. Ask me about skills, roles, or interview tips!";

        // Java Developer
        if (msg.contains("java developer") || msg.contains("java backend"))
            return "To become a Java Developer, focus on: ✅ Core Java, ✅ Spring Boot, ✅ REST APIs, ✅ MySQL/PostgreSQL, ✅ Git, ✅ Maven, and ✅ Docker. Practice building small projects on GitHub. Check our Match page to see your current fit!";

        // Frontend
        if (msg.contains("frontend") || msg.contains("front end") || msg.contains("react") || msg.contains("angular"))
            return "For Frontend Developer: ✅ HTML, ✅ CSS, ✅ JavaScript, ✅ React/Angular, ✅ TypeScript, ✅ REST APIs, ✅ Git. Build 3-5 UI projects on GitHub. Learn responsive design.";

        // Data Science / ML
        if (msg.contains("data science") || msg.contains("data scientist"))
            return "Data Scientist path: ✅ Python, ✅ Pandas, ✅ NumPy, ✅ SQL, ✅ Machine Learning, ✅ Deep Learning, ✅ TensorFlow/PyTorch. Kaggle competitions are great practice!";

        if (msg.contains("machine learning") || msg.contains("ml engineer") || msg.contains("ml "))
            return "ML Engineer roadmap: ✅ Python, ✅ ML Algorithms, ✅ TensorFlow, ✅ PyTorch, ✅ Docker, ✅ AWS. Learn model deployment + MLOps concepts.";

        // Full Stack
        if (msg.contains("full stack") || msg.contains("fullstack"))
            return "Full Stack Developer needs: ✅ Java/Spring Boot (backend), ✅ React (frontend), ✅ MySQL, ✅ REST APIs, ✅ Docker. Aim to build complete end-to-end projects.";

        // DevOps
        if (msg.contains("devops") || msg.contains("dev ops"))
            return "DevOps Engineer roadmap: ✅ Linux, ✅ Docker, ✅ Kubernetes, ✅ AWS/Azure, ✅ Jenkins, ✅ CI/CD, ✅ Bash scripting. Start with Docker + Linux basics.";

        // Python
        if (msg.contains("python"))
            return "Python Developer path: ✅ Python basics, ✅ Django/Flask, ✅ REST APIs, ✅ PostgreSQL, ✅ Git, ✅ Docker. Build 3 backend projects.";

        // Interview prep
        if (msg.contains("interview"))
            return "🎤 Interview tips:\n1. Master DSA basics (arrays, strings, hash maps)\n2. Practice 2-3 projects you can explain deeply\n3. Prepare STAR answers for behavior questions\n4. Research the company\n5. Ask thoughtful questions at the end";

        // Resume
        if (msg.contains("resume") || msg.contains("cv"))
            return "📄 Resume tips:\n1. Keep it 1 page (fresher) / 2 pages max\n2. Lead with a strong summary\n3. Use action verbs (Built, Designed, Led)\n4. Quantify impact (e.g., '40% faster')\n5. Add GitHub + LinkedIn links\n6. Match keywords from job description";

        // Fresher / Start
        if (msg.contains("fresher") || msg.contains("beginner") || msg.contains("start"))
            return "🌱 Fresher roadmap:\n1. Pick ONE language (Java/Python/JS)\n2. Build 3-5 projects\n3. Push all code to GitHub\n4. Write a strong resume\n5. Apply on LinkedIn + Naukri\n6. Solve 100+ DSA problems";

        // Skills
        if (msg.contains("skill") || msg.contains("learn"))
            return "🎓 To discover your skill gaps, upload your resume and visit our Career Guide page. It shows exactly which skills to learn next with YouTube tutorials!";

        // Salary
        if (msg.contains("salary") || msg.contains("package") || msg.contains("pay"))
            return "💰 Salary depends on role, city, and experience. Freshers in India: 3-6 LPA (services), 8-15 LPA (product companies). Focus on strong skills + projects for better offers!";

        // Which role
        if (msg.contains("which role") || msg.contains("what role") || msg.contains("best role"))
            return "🎯 Upload your resume → go to Dashboard → click your card. You'll see your top 5 matching roles with scores!";

        // Certification
        if (msg.contains("certification") || msg.contains("certificate") || msg.contains("course"))
            return "📜 Recommended certifications:\n• AWS Cloud Practitioner\n• Oracle Java (OCA/OCP)\n• Google Data Analytics\n• Microsoft Azure Fundamentals\n• Docker Certified Associate\nFree: freeCodeCamp, Coursera (audit mode)";

        // Thank you
        if (msg.matches(".*\\b(thanks|thank you|thankyou)\\b.*"))
            return "You're welcome! 😊 Ask me anything else about your career!";

        // Goodbye
        if (msg.matches(".*\\b(bye|goodbye|see you)\\b.*"))
            return "Good luck with your career journey! 🚀 Come back anytime!";

        // Help
        if (msg.contains("help") || msg.contains("what can you do"))
            return "I can help with:\n• Career roadmaps (Java, Python, Frontend, etc.)\n• Interview preparation\n• Resume tips\n• Skill recommendations\n• Salary insights\nJust type your question!";

        // Default
        return "🤔 I'm not sure about that yet. Try asking:\n• 'How to become a Java Developer?'\n• 'Interview tips'\n• 'Resume tips'\n• 'What skills should I learn?'";
    }
}