package auth.server.services;

import auth.server.entities.Employee;
import auth.server.entities.Role;
import auth.server.repositories.EmployeeRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    public void saveEmployee(OAuth2User principal) {

        String email = principal.getAttribute("email");
        String name = principal.getAttribute("given_name");
        String surname = (principal.getAttribute("family_name") != null) ? principal.getAttribute("family_name") : "default_surname";
        Employee employee = employeeRepository.findByEmployeeEmail(email).orElse(null);
        if (employee == null) {
            employee = new Employee();
            employee.setEmployeeName(name);
            employee.setEmployeeSurName(surname);
            employee.setEmployeeEmail(email);
            employee.setRole(Role.DEFAULT);
            employeeRepository.save(employee);
        }
    }
}
