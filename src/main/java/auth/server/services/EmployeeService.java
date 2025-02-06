package auth.server.services;

import auth.server.entities.Employee;
import auth.server.entities.Role;
import auth.server.exceptions.ResourceNotFoundException;
import auth.server.repositories.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

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

    public Page<Employee> getEmployees(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findAll(pageable);
    }
    public void deleteEmployeById(Long employee_id) {
        this.employeeRepository.deleteById(employee_id);
    }
    public void assignRole(Long employeeId, String role) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("employee not found"));
        Role newRole = Role.valueOf(role.toUpperCase());
        employee.setRole(newRole);
        this.employeeRepository.save(employee);
    }
    public Employee findEmployeeById(Long employeeId){
        return this.employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("employee not found"));
    }
}
