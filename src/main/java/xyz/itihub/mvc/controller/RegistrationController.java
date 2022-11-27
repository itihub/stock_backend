package xyz.itihub.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.itihub.mvc.dto.UserRegistration;
import xyz.itihub.mvc.service.UserService;

/**
 * 登录注册控制器
 */
@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegistration userRegistrationDto() {
        return new UserRegistration();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping
    public String registerUser(@ModelAttribute("user") UserRegistration userRegistration) {
        userService.save(userRegistration);
        return "redirect:/registration?success";
    }

}
