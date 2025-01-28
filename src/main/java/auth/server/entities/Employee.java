package auth.server.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @Column(length = 20,name = "employee_name")
    private String employeeName;
    @Column
    private String password;
    @Column(name = "employee_surname", nullable = false)
    private String employeeSurName;
    @Column(length = 50, name = "employee_email", unique = true)
    private String employeeEmail;
    @Enumerated(EnumType.STRING)
    @Column(name = "employee_role")
    private Role role;


}