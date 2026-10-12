package ai_smart_recruiter.ai;

import ai_smart_recruiter.model.Resume;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AiMatchingService {

    // ---------- ROLE → REQUIRED SKILLS ----------
    private static final Map<String, List<String>> ROLE_SKILLS = new LinkedHashMap<>();

    // ---------- SKILL → YOUTUBE SEARCH URL ----------
    private static final Map<String, String> SKILL_LINKS = new HashMap<>();

    static {
        ROLE_SKILLS.put("Java Developer",
            Arrays.asList("java", "spring", "spring boot", "hibernate", "jpa",
                          "maven", "rest", "api", "mysql", "git"));

        ROLE_SKILLS.put("Java Backend Developer",
            Arrays.asList("java", "spring boot", "rest", "api", "microservices",
                          "mysql", "postgresql", "docker", "aws", "git", "maven"));

        ROLE_SKILLS.put("Frontend Developer",
            Arrays.asList("html", "css", "javascript", "react", "angular",
                          "typescript", "git", "rest", "api"));

        ROLE_SKILLS.put("Full Stack Developer",
            Arrays.asList("java", "spring boot", "javascript", "react",
                          "html", "css", "mysql", "rest", "api", "git", "docker"));

        ROLE_SKILLS.put("Python Developer",
            Arrays.asList("python", "django", "flask", "rest", "api",
                          "postgresql", "git", "docker"));

        ROLE_SKILLS.put("Data Scientist",
            Arrays.asList("python", "machine learning", "deep learning",
                          "pandas", "numpy", "sql", "tensorflow", "pytorch"));

        ROLE_SKILLS.put("DevOps Engineer",
            Arrays.asList("docker", "kubernetes", "aws", "azure", "gcp",
                          "jenkins", "ci/cd", "linux", "bash", "git"));

        ROLE_SKILLS.put("Software Engineer",
            Arrays.asList("java", "python", "git", "rest", "api",
                          "sql", "docker", "linux"));

        ROLE_SKILLS.put("Mobile Developer",
            Arrays.asList("java", "kotlin", "swift", "react", "rest", "api", "git"));

        ROLE_SKILLS.put("Machine Learning Engineer",
            Arrays.asList("python", "machine learning", "deep learning",
                          "tensorflow", "pytorch", "numpy", "pandas", "docker", "aws"));

        // ---------- YouTube links for each skill ----------
        SKILL_LINKS.put("java",              "https://www.youtube.com/results?search_query=java+full+course");
        SKILL_LINKS.put("spring",            "https://www.youtube.com/results?search_query=spring+framework+tutorial");
        SKILL_LINKS.put("spring boot",       "https://www.youtube.com/results?search_query=spring+boot+full+course");
        SKILL_LINKS.put("hibernate",         "https://www.youtube.com/results?search_query=hibernate+tutorial");
        SKILL_LINKS.put("jpa",               "https://www.youtube.com/results?search_query=spring+data+jpa+tutorial");
        SKILL_LINKS.put("maven",             "https://www.youtube.com/results?search_query=maven+tutorial");
        SKILL_LINKS.put("rest",              "https://www.youtube.com/results?search_query=rest+api+tutorial");
        SKILL_LINKS.put("api",               "https://www.youtube.com/results?search_query=api+tutorial+for+beginners");
        SKILL_LINKS.put("mysql",             "https://www.youtube.com/results?search_query=mysql+tutorial");
        SKILL_LINKS.put("git",               "https://www.youtube.com/results?search_query=git+and+github+tutorial");
        SKILL_LINKS.put("html",              "https://www.youtube.com/results?search_query=html+tutorial");
        SKILL_LINKS.put("css",               "https://www.youtube.com/results?search_query=css+tutorial");
        SKILL_LINKS.put("javascript",        "https://www.youtube.com/results?search_query=javascript+full+course");
        SKILL_LINKS.put("react",             "https://www.youtube.com/results?search_query=react+js+full+course");
        SKILL_LINKS.put("angular",           "https://www.youtube.com/results?search_query=angular+tutorial");
        SKILL_LINKS.put("typescript",        "https://www.youtube.com/results?search_query=typescript+tutorial");
        SKILL_LINKS.put("python",            "https://www.youtube.com/results?search_query=python+full+course");
        SKILL_LINKS.put("django",            "https://www.youtube.com/results?search_query=django+full+course");
        SKILL_LINKS.put("flask",             "https://www.youtube.com/results?search_query=flask+tutorial");
        SKILL_LINKS.put("postgresql",        "https://www.youtube.com/results?search_query=postgresql+tutorial");
        SKILL_LINKS.put("docker",            "https://www.youtube.com/results?search_query=docker+tutorial");
        SKILL_LINKS.put("kubernetes",        "https://www.youtube.com/results?search_query=kubernetes+tutorial");
        SKILL_LINKS.put("aws",               "https://www.youtube.com/results?search_query=aws+for+beginners");
        SKILL_LINKS.put("azure",             "https://www.youtube.com/results?search_query=azure+fundamentals");
        SKILL_LINKS.put("gcp",               "https://www.youtube.com/results?search_query=google+cloud+platform+tutorial");
        SKILL_LINKS.put("machine learning",  "https://www.youtube.com/results?search_query=machine+learning+full+course");
        SKILL_LINKS.put("deep learning",     "https://www.youtube.com/results?search_query=deep+learning+course");
        SKILL_LINKS.put("pandas",            "https://www.youtube.com/results?search_query=pandas+tutorial");
        SKILL_LINKS.put("numpy",             "https://www.youtube.com/results?search_query=numpy+tutorial");
        SKILL_LINKS.put("tensorflow",        "https://www.youtube.com/results?search_query=tensorflow+tutorial");
        SKILL_LINKS.put("pytorch",           "https://www.youtube.com/results?search_query=pytorch+tutorial");
        SKILL_LINKS.put("sql",               "https://www.youtube.com/results?search_query=sql+full+course");
        SKILL_LINKS.put("microservices",     "https://www.youtube.com/results?search_query=microservices+tutorial");
        SKILL_LINKS.put("jenkins",           "https://www.youtube.com/results?search_query=jenkins+tutorial");
        SKILL_LINKS.put("ci/cd",             "https://www.youtube.com/results?search_query=ci+cd+pipeline+tutorial");
        SKILL_LINKS.put("linux",             "https://www.youtube.com/results?search_query=linux+tutorial");
        SKILL_LINKS.put("bash",              "https://www.youtube.com/results?search_query=bash+scripting+tutorial");
        SKILL_LINKS.put("kotlin",            "https://www.youtube.com/results?search_query=kotlin+android+tutorial");
        SKILL_LINKS.put("swift",             "https://www.youtube.com/results?search_query=swift+ios+tutorial");
    }

    public Set<String> getAllRoles() {
        return ROLE_SKILLS.keySet();
    }

    public List<String> getSkillsForRole(String roleInput) {
        if (roleInput == null || roleInput.trim().isEmpty()) return Collections.emptyList();
        String q = roleInput.toLowerCase().trim();

        for (Map.Entry<String, List<String>> e : ROLE_SKILLS.entrySet()) {
            if (e.getKey().toLowerCase().equals(q)) return e.getValue();
        }
        for (Map.Entry<String, List<String>> e : ROLE_SKILLS.entrySet()) {
            if (e.getKey().toLowerCase().contains(q) || q.contains(e.getKey().toLowerCase())) {
                return e.getValue();
            }
        }
        Set<String> queryWords = new HashSet<>(Arrays.asList(q.split("\\s+")));
        for (Map.Entry<String, List<String>> e : ROLE_SKILLS.entrySet()) {
            Set<String> roleWords = new HashSet<>(Arrays.asList(e.getKey().toLowerCase().split("\\s+")));
            roleWords.retainAll(queryWords);
            if (!roleWords.isEmpty()) return e.getValue();
        }
        return Collections.emptyList();
    }

    public Set<String> extractSkills(String text) {
        if (text == null) return Collections.emptySet();
        String lower = text.toLowerCase();
        Set<String> allSkills = new HashSet<>();
        for (List<String> skills : ROLE_SKILLS.values()) allSkills.addAll(skills);
        return allSkills.stream().filter(lower::contains).collect(Collectors.toSet());
    }

    public double matchScore(Resume resume, List<String> requiredSkills) {
        if (requiredSkills == null || requiredSkills.isEmpty()) return 0.0;
        Set<String> resumeSkills = extractSkills(resume.getRawText());
        long matched = requiredSkills.stream().filter(resumeSkills::contains).count();
        double score = (matched * 100.0) / requiredSkills.size();
        return Math.round(score * 10.0) / 10.0;
    }

    public List<String> matchedSkills(Resume resume, List<String> requiredSkills) {
        Set<String> resumeSkills = extractSkills(resume.getRawText());
        return requiredSkills.stream().filter(resumeSkills::contains).sorted().collect(Collectors.toList());
    }

    public List<String> missingSkills(Resume resume, List<String> requiredSkills) {
        Set<String> resumeSkills = extractSkills(resume.getRawText());
        return requiredSkills.stream().filter(s -> !resumeSkills.contains(s)).sorted().collect(Collectors.toList());
    }

    /** Returns list of maps: {skill, link} for missing skills */
    public List<Map<String, String>> missingSkillsWithLinks(Resume resume, List<String> requiredSkills) {
        List<String> missing = missingSkills(resume, requiredSkills);
        List<Map<String, String>> result = new ArrayList<>();
        for (String skill : missing) {
            Map<String, String> m = new HashMap<>();
            m.put("skill", skill);
            m.put("link", SKILL_LINKS.getOrDefault(skill,
                    "https://www.youtube.com/results?search_query=" + skill.replace(" ", "+") + "+tutorial"));
            result.add(m);
        }
        return result;
    }

    public String verdict(double score) {
        if (score >= 75) return "Excellent Match";
        if (score >= 50) return "Good Match";
        if (score >= 25) return "Partial Match";
        return "Weak Match";
    }
    /** Find best-matching roles for a resume (top 3) */
    public List<Map<String, Object>> getBestRolesForResume(Resume resume, int topN) {
        List<Map<String, Object>> results = new ArrayList<>();
        for (Map.Entry<String, List<String>> e : ROLE_SKILLS.entrySet()) {
            String role = e.getKey();
            List<String> required = e.getValue();
            double score = matchScore(resume, required);
            Map<String, Object> m = new HashMap<>();
            m.put("role", role);
            m.put("score", score);
            m.put("matched", matchedSkills(resume, required).size());
            m.put("total", required.size());
            results.add(m);
        }
        results.sort((a, b) -> Double.compare((Double) b.get("score"), (Double) a.get("score")));
        return results.size() > topN ? results.subList(0, topN) : results;
    }

    /** All skills this resume has (from master list) */
    public List<String> getResumeSkills(Resume resume) {
        return new ArrayList<>(extractSkills(resume.getRawText()));
    }
}