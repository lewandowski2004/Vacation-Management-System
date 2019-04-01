package lewandowski.demo.Service;

import lewandowski.demo.DTO.VacationTypeDto;
import lewandowski.demo.Model.VacationType;

import java.util.List;

public interface VacationTypeService {

    /*Entity method*/

    List<VacationType> findAllVacationsType();
    VacationType findVacationTypeById(int id);

    /*Dto method*/

    VacationTypeDto findVacationTypeDtoById(int id);
    List<VacationTypeDto> findAllVacationsTypeDto();
    VacationType getVacationType(VacationTypeDto vacationTypeDto);
    VacationTypeDto getVacationTypeDto(VacationType vacationType);
}
