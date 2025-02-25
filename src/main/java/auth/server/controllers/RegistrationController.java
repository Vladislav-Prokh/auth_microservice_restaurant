package auth.server.controllers;

import auth.server.dto.RegistrationRequest;
import auth.server.exceptions.UserAlreadyExistsException;
import auth.server.exceptions.VerificationCodeException;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import auth.server.services.RegistrationService;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {


    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public ResponseEntity<?> verifyUser(@RequestBody RegistrationRequest request, Model model) {
        try {
            registrationService.verifyUser(request);
            return ResponseEntity.ok().body("{\"message\":\"code send successfully\"}");
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body("{\"message\":\"User already exists\"}");
        } catch (VerificationCodeException e) {
            return ResponseEntity.badRequest().body("{\"message\":\"Verification code was not sent\"}");
        }
    }

    @PostMapping("/code")
    public String registerUser(@ModelAttribute("code") String code, Model model) {
        try {
            model.addAttribute("message", "User registered successfully");
            return "redirect:/login";
        }
        catch(VerificationCodeException e){
            model.addAttribute("error", e.getMessage());
            return "redirect:/registration";
        }
    }
}
