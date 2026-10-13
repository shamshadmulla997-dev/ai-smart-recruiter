package ai_smart_recruiter.service;

import ai_smart_recruiter.model.User;
import ai_smart_recruiter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public User registerUser(String username, String rawPassword,
                             String role, String fullName, String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));  // BCrypt hash
        user.setRole(role);
        user.setFullName(fullName);
        user.setEmail(email);
        return userRepository.save(user);
    }
}