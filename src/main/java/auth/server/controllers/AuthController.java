package auth.server.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "login-template";
    }
    @GetMapping("/register")
    public String register() {
        return "registration-template";
    }
    @GetMapping("/email-verification")
    public String emailVerification() {
        return "email-verification-template";
    }
}
