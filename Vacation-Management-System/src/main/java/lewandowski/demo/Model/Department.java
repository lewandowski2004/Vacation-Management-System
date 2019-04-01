package lewandowski.demo.Model;


import lombok.*;

import javax.persistence.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "department_id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy="department", fetch=FetchType.EAGER)
    private Set<Position> positions;

    @OneToOne(mappedBy = "department")
    private Employee employee;


    public Department(String name, Set<Position> positions, Employee employee) {
        this.name = name;
        this.positions = positions;
        this.employee = employee;
    }


}
