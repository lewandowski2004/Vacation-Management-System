package lewandowski.demo.DTO;

import lewandowski.demo.Model.Application;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class ApplicationStatusDto {

    private int id;

    private String status;

    private ApplicationDto applicationDto;
}
