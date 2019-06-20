package lewandowski.demo.Model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "employee_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "pesel")
    private String pesel;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(name = "date_of_addition")
    private Date dateOfAddition;

    @Column(name = "address_line_1")
    private String addressLine1;

    @Column(name = "address_line_2")
    private String addressLine2;

    @Column(name = "city")
    private String city;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "password")
    private String password;

    @Column(name = "active")
    private int active;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "employee_role",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "employee", fetch=FetchType.EAGER)
    private Set<Application> applications;

    @OneToMany(mappedBy="employee", fetch=FetchType.EAGER)
    private Set<VacationBalance> vacationBalances;

    @OneToOne
    @JoinColumn(name = "department_id")
    private Department department ;

    @OneToOne
    @JoinColumn(name = "position_id")
    private Position position;

    @Transient
    private String confirmPassword;

    @Transient
    private int nrRoli;

    @Transient
    private int nrDepartment;

    @Transient
    private String newPassword;

    @Transient
    private String oldPassword;

    public Employee(String name, String lastName, String email, String pesel, Date dateOfBirth, Date dateOfAddition, String addressLine1, String addressLine2, String city, String zipCode, String phoneNumber, String password, int active, Set<Role> roles, Set<Application> applications, Set<VacationBalance> vacationBalances, Department department, Position position, String confirmPassword, int nrRoli, int nrDepartment, String newPassword, String oldPassword) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.pesel = pesel;
        this.dateOfBirth = dateOfBirth;
        this.dateOfAddition = dateOfAddition;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.active = active;
        this.roles = roles;
        this.applications = applications;
        this.vacationBalances = vacationBalances;
        this.department = department;
        this.position = position;
        this.confirmPassword = confirmPassword;
        this.nrRoli = nrRoli;
        this.nrDepartment = nrDepartment;
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
    }
}
