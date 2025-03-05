package auth.server.controllers;

import auth.server.dto.RegistrationRequest;
import auth.server.exceptions.UserAlreadyExistsException;
import auth.server.exceptions.VerificationCodeException;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import auth.server.services.RegistrationService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    private final MessageSource messageSource;

    public RegistrationController(RegistrationService registrationService, MessageSource messageSource) {
        this.registrationService = registrationService;
        this.messageSource = messageSource;
    }

    @PostMapping
    public ResponseEntity<?> verifyUser(@Valid  @RequestBody RegistrationRequest request, Locale locale) {
        try {
            registrationService.verifyUser(request);
            String message = messageSource.getMessage("registration.code_sent", null, locale);
            return ResponseEntity.ok().body(Map.of("message", message));
        } catch (UserAlreadyExistsException e) {
            String message = messageSource.getMessage("registration.user_exists", null, locale);
            return ResponseEntity.badRequest().body(Map.of("message", message));
        } catch (VerificationCodeException e) {
            String message = messageSource.getMessage("registration.verification_failed", null, locale);
            return ResponseEntity.badRequest().body(Map.of("message", message));
        }
    }

    @PostMapping("/code")
    public ResponseEntity<?>  registerUser(@RequestParam("code") String code, Locale locale) {
        try {
            registrationService.register(code);
            String message = messageSource.getMessage("registration.success", null, locale);
            return ResponseEntity.badRequest().body(Map.of("message", message));
        } catch (VerificationCodeException e) {
            String message = messageSource.getMessage("registration.verification_failed", null, locale);
            return ResponseEntity.badRequest().body(Map.of("error", message));
        }

    }

}
