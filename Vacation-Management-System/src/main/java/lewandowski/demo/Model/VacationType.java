package lewandowski.demo.Model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "vacation_type")
public class VacationType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "vacation_type_id")
    private int id;

    @Column(name = "type")
    private String type;

    @OneToOne(mappedBy = "vacationType")
    private Application application;
}
