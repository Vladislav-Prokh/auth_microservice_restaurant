package auth.server.services;

import auth.server.dto.RegistrationResponse;
import auth.server.repositories.EmployeeRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import auth.server.entities.Employee;
import auth.server.entities.Role;


@Service
public class RegistrationService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public RegistrationResponse register(String name, String surname, String email, String rawPassword) {
        if (employeeRepository.findByEmployeeEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already exists: " + email);
        }
        Employee employee = new Employee();
        employee.setEmployeeName(name);
        employee.setEmployeeSurName(surname);
        employee.setEmployeeEmail(email);
        employee.setPassword(passwordEncoder.encode(rawPassword));
        employee.setRole(Role.DEFAULT);
        employee = employeeRepository.save(employee);
        return new RegistrationResponse(employee.getEmployeeName(),
                employee.getEmployeeSurName(), employee.getEmployeeEmail());
    }

    public String sendVerifyCode(String email) {
        return passwordEncoder.encode(email);
    }

}
