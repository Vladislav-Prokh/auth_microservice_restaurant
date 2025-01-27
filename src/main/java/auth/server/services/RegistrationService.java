package auth.server.services;

import auth.server.DTO.RegistrationResponse;
import auth.server.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public RegistrationResponse register(String name, String surname, String email, String rawPassword, Role role) {
        if (employeeRepository.findByEmployeeEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already exists: " + email);
        }
        Employee employee = new Employee();
        employee.setEmployeeName(name);
        employee.setEmployeeSurName(surname);
        employee.setEmployeeEmail(email);
        employee.setPassword(passwordEncoder.encode(rawPassword));
        employee.setRole(role);
        employee = employeeRepository.save(employee);
        return new RegistrationResponse(employee.getEmployeeName(),
                employee.getEmployeeSurName(), employee.getEmployeeEmail(), employee.getRole());
    }
}
