package edu.josephlopotko.products.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.josephlopotko.products.data.UsersRepository;
import edu.josephlopotko.products.models.UserEntity;

@Controller
@RequestMapping("/admin")
public class UserAdminController {

    private final UsersRepository usersRepository;

    public UserAdminController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<UserEntity> users = (List<UserEntity>) usersRepository.findAll();
        model.addAttribute("users", users);
        return "user/userAdmin";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditUserForm(@PathVariable int id, Model model) {
        UserEntity user = usersRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + id));
        model.addAttribute("user", user);
        return "user/editUser";
    }

    @PostMapping("/users/edit")
    public String updateUser(@ModelAttribute UserEntity user) {
        // Get the existing user to save the password
        UserEntity existingUser = usersRepository.findById(user.getId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + user.getId()));
        
        // Update only role and enabled status
        existingUser.setRole(user.getRole());
        existingUser.setEnabled(user.isEnabled());
        
        // Save the updated user
        usersRepository.save(existingUser);
        
        return "redirect:/admin/users";
    }

    @GetMapping("/users/delete/{id}")
    public String showDeleteConfirmation(@PathVariable int id, Model model) {
        UserEntity user = usersRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + id));
        model.addAttribute("user", user);
        return "user/confirmDeleteUser";
    }

    @PostMapping("/users/delete")
    public String deleteUser(@RequestParam int id) {
        usersRepository.deleteById(id);
        return "redirect:/admin/users";
    }
}
