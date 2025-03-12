package auth.server.controllers;

import auth.server.dto.RoleRequest;
import auth.server.entities.Employee;
import auth.server.services.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{employee-id}")
    public Employee findById(@PathVariable("employee-id") Long employee_id){
        return this.employeeService.findEmployeeById(employee_id);
    }

    @GetMapping
    public Page<Employee> findAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return this.employeeService.getEmployees(page, size);
    }

    @PostMapping("/{employeeId}/role")
    public ResponseEntity<String> assignRole(@PathVariable("employeeId") Long employeeId,
                                             @RequestBody RoleRequest roleRequest) {
        try {
            employeeService.assignRole(employeeId, roleRequest.getRole());
            return ResponseEntity.ok("Role assigned successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to assign role");
        }
    }

    @PostMapping("/{employeeId}/{email}")
    public ResponseEntity<String> updateEmployeeEmail(@PathVariable("employeeId") Long employeeId,
                                             @PathVariable String email) {
        try {
            employeeService.changeEmployeeEmail(employeeId, email);
            return ResponseEntity.ok("Role assigned successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to assign role");
        }
    }

    @DeleteMapping("/{employee-id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable("employee-id") Long employee_id) {
        this.employeeService.deleteEmployeById(employee_id);
        return ResponseEntity.noContent().build();
    }

}
