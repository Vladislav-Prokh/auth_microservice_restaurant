package auth.server.entities;


import auth.server.annotations.StrongPassword;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
   // @StrongPassword
    @Column
    private String password;
    @Column(name = "employee_surname", nullable = false)
    private String employeeSurName;
    @Email
    @Column(length = 50, name = "employee_email", unique = true)
    private String employeeEmail;
    @Enumerated(EnumType.STRING)
    @Column(name = "employee_role")
    private Role role;

    @Column(length = 2)
    private String locale;

    public Employee(String email, String name, String surname, String password, String locale) {
        this.employeeEmail = email;
        this.employeeName = name;
        this.employeeSurName = surname;
        this.password = password;
        this.locale = locale;
        this.role = Role.DEFAULT;
    }
}
