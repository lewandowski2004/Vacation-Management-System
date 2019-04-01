package lewandowski.demo.Model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "vacation_balance")
public class VacationBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "vacation_balance_id")
    private int id;

    @Column(name = "vacation_limit")
    private int vacationLimit;

    @Column(name = "annual_vacation")
    private int annualVacation;

    @Column(name = "vacation_leave")
    private int vacationLeave;

    @Column(name = "emergency_vacation")
    private int emergencyVacation;

    @Column(name = "year")
    @Temporal(TemporalType.DATE)
    private Date year;

    @ManyToOne
    @JoinColumn(name="employee_id")
    private Employee employee;
}
