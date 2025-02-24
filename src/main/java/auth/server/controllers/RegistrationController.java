package auth.server.controllers;

import auth.server.dto.RegistrationRequest;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import auth.server.services.RegistrationService;

@RestController
@RequestMapping("/api/register")
public class RegistrationController {

    @Autowired
    private JavaMailSender javaMailSender;

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public void registerUser(@RequestBody RegistrationRequest registrationRequest) {
        String userToSend = registrationRequest.getEmail();
        sendVerificationEmail(userToSend);
    }

    private void sendVerificationEmail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        String emailTopic = "Verification email restaurant service";
        String verificationCode = generateVerificationCode();
        message.setTo(email);
        message.setSubject(emailTopic);
        message.setText("Your verification code, which expires in 5 minutes: "+ verificationCode);
        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String generateVerificationCode(){
        int verificationCodeSize = 4;
        int[] codeNumbers = new int[verificationCodeSize];
        for(int i = 0; i < verificationCodeSize; i++){
            codeNumbers[i] = (int)(Math.random()*9+1);
        }
        return  StringUtils.join(ArrayUtils.toObject(codeNumbers), " - ");
    }
}
