package edu.josephlopotko.products.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import edu.josephlopotko.products.data.UsersRepository;
import edu.josephlopotko.products.models.UserEntity;

@Controller
public class AuthController {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/orders";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserEntity());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserEntity user) {
        // Encode the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Assign default role
        user.setRole("ROLE_USER");
        
        // Enable the user
        user.setEnabled(true);
        
        // Save the user
        usersRepository.save(user);
        
        // Redirect to login page
        return "redirect:/login";
    }
}
