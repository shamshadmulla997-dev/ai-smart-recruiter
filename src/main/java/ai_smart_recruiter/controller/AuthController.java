package ai_smart_recruiter.controller;

import ai_smart_recruiter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String confirmPassword,
                               @RequestParam String role,
                               @RequestParam(required = false) String fullName,
                               @RequestParam(required = false) String email,
                               Model model) {

        // Validation
        if (username.length() < 3) {
            model.addAttribute("error", "Username must be at least 3 characters");
            return "register";
        }
        if (password.length() < 6) {
            model.addAttribute("error", "Password must be at least 6 characters");
            return "register";
        }
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "register";
        }
        if (userService.usernameExists(username)) {
            model.addAttribute("error", "Username already taken");
            return "register";
        }

        userService.registerUser(username, password, role, fullName, email);
        return "redirect:/login?registered";
    }
}