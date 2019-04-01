package lewandowski.demo.Model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "application_status")
public class ApplicationStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "application_status_id")
    private int id;

    @Column(name = "status")
    private String status;

    @OneToOne(mappedBy = "applicationStatus")
    private Application application;

    public ApplicationStatus(String status, Application application) {
        this.status = status;
        this.application = application;
    }

}
