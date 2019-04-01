package lewandowski.demo.DTO;

import lewandowski.demo.Model.Application;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class VacationTypeDto {

    private int id;

    private String type;

    private ApplicationDto applicationDto;

}
