package lewandowski.demo.Model;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "application")
public class Application {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "application_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "date_of_addition")
    @Temporal(TemporalType.DATE)
    private Date dateOfAddition;

    @Column(name = "start_of_vacation")
    @Temporal(TemporalType.DATE)
    private Date startOfVacation;

    @Column(name = "end_of_vacation")
    @Temporal(TemporalType.DATE)
    private Date endOfVacation;

    @Column(name = "vacation_days")
    private int vacationDays;

    @Column(name = "vacation_plan")
    private boolean vacationPlan;

    @OneToOne
    @JoinColumn(name = "application_status_id")
    private ApplicationStatus applicationStatus;

    @OneToOne
    @JoinColumn(name = "vacation_type_id")
    private VacationType vacationType;

    @Column(name = "replacement")
    private String replacement;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Transient
    private int nrStatus;
}
