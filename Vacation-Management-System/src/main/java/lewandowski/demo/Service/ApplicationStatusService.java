package lewandowski.demo.Service;


import lewandowski.demo.DTO.ApplicationStatusDto;
import lewandowski.demo.Model.ApplicationStatus;

import java.util.List;

public interface ApplicationStatusService {

    /*Entity method*/

    ApplicationStatus findApplicationStatusById(int id);
    List<ApplicationStatus> findAllApplicationsStatus();
    ApplicationStatus findApplicationStatusByStatus(String status);

    /*DTO method*/

    ApplicationStatusDto findApplicationStatusDtoById(int id);
    List<ApplicationStatusDto> findAllApplicationsStatusDto();
    ApplicationStatusDto findApplicationStatusDtoByStatus(String status);
    ApplicationStatus getApplicationStatus(ApplicationStatusDto applicationStatusDto);
    ApplicationStatusDto getApplicationStatusDto(ApplicationStatus applicationStatus);
}
