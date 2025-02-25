package auth.server.services;

import auth.server.dto.RegistrationRequest;
import auth.server.dto.RegistrationResponse;
import auth.server.exceptions.UserAlreadyExistsException;
import auth.server.exceptions.VerificationCodeException;
import auth.server.repositories.EmployeeRepository;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import auth.server.entities.Employee;
import java.util.concurrent.TimeUnit;

@Service
public class RegistrationService {

    @Autowired
    private JavaMailSender javaMailSender;

    private final RedisTemplate<String, Employee> redisTemplate;


    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder, RedisTemplate<String, Employee> redisTemplate) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.redisTemplate = redisTemplate;
    }

    public RegistrationResponse register(String code) {

        Employee employee = getVerificationCodeFromDb(code);
        if(employee == null) {
            throw new VerificationCodeException("Invalid verification code or it expired");
        }
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        this.employeeRepository.save(employee);
        return new RegistrationResponse(employee.getEmployeeName(),
                employee.getEmployeeSurName(), employee.getEmployeeEmail());
    }

    public void verifyUser(RegistrationRequest registrationRequest) {
        if(this.employeeRepository.findByEmployeeEmail(registrationRequest.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists: " + registrationRequest.getEmail());
        }
        String userToSend = registrationRequest.getEmail();
        String verificationCode = generateVerificationCode();
        boolean isSentCode = sendVerificationEmail(userToSend, verificationCode);
        if(isSentCode){
            Employee employee = new Employee(
                    registrationRequest.getEmail(),
                    registrationRequest.getName(),
                    registrationRequest.getSurname(),
                    registrationRequest.getPassword()
            );
            saveVerificationCodeToDb(verificationCode, employee);
        }
        else {
            throw new VerificationCodeException("Verification code was not sent");
        }
    }

    private boolean sendVerificationEmail(String email, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        String emailTopic = "Verification email restaurant service";
        message.setTo(email);
        message.setSubject(emailTopic);
        message.setText("Your verification code, which expires in 5 minutes: "+ verificationCode);
        try{
            javaMailSender.send(message);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    private void saveVerificationCodeToDb(String verificationCode, Employee employee) {
        redisTemplate.opsForValue().set(verificationCode, employee, 5, TimeUnit.MINUTES);
    }

    public Employee getVerificationCodeFromDb(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    private String generateVerificationCode(){
        int verificationCodeSize = 4;
        int[] codeNumbers = new int[verificationCodeSize];
        for(int i = 0; i < verificationCodeSize; i++){
            codeNumbers[i] = (int)(Math.random()*9+1);
        }
        return  StringUtils.join(ArrayUtils.toObject(codeNumbers));
    }
}
