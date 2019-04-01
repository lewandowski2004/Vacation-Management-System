package lewandowski.demo.Model;

import javax.persistence.*;
import javax.validation.constraints.Size;

/*@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor*/
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "address_id")
    private int id;

    //@NotNull
    @Size(max = 100)
    @Column(name = "address_line_1")
    private String addressLine1;

    @Size(max = 100)
    @Column(name = "address_line_2")
    private String addressLine2;

    //@NotNull
    @Size(max = 100)
    @Column(name = "city")
    private String city;

    //@NotNull
    @Size(max = 6)
    @Column(name = "zip_code")
    private String zipCode;
/*
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;*/
}
