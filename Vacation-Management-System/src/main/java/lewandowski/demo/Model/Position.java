package lewandowski.demo.Model;


import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "position")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "position_id")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name="department_id")
    private Department department;

    @OneToOne(mappedBy = "position")
    private Employee employee;

    @Transient
    private int nrDepartment;


    public Position(String name, Department department, Employee employee) {
        this.name = name;
        this.department = department;
        this.employee = employee;
    }
}
