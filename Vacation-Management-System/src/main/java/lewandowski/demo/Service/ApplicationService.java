package lewandowski.demo.Service;

import lewandowski.demo.DTO.ApplicationDto;
import lewandowski.demo.Model.Application;

import java.util.List;
import java.util.UUID;

public interface ApplicationService {

    /*Entity method*/

    Application findApplicationById(UUID id);
    List<Application> findAllApplications();
    List<Application> findApplicationsByEmployee_IdAndVacationPlanOrderByDateOfAdditionDesc(UUID employeeId, boolean vacationPlan);
    List<Application> findApplicationsEmployeeByDepartment(Integer departmentId, UUID employeeId);
    List<Application> findAllApplicationsAdmin(UUID employeeId);
    void deleteApplicationById(UUID applicationId);
    void deleteApplication(Application application);
    void deleteApplicationByEmployeeId(UUID employeeId);
    void saveApplication(Application application);
    void updateStatusApplication(Integer applicationStatus,UUID id);

    /*DTO method*/

    ApplicationDto findApplicationDtoById(UUID id);
    List<ApplicationDto> findAllApplicationsDto();
    List<ApplicationDto> findApplicationsByEmployee_IdAndVacationPlanOrderByDateOfAdditionDescDto(UUID employeeId, boolean vacationPlan);
    List<ApplicationDto> findAllApplicationsDtoAdmin(UUID employeeId);
    List<ApplicationDto> findAllVacationPlansDtoAdmin(UUID employeeId);
    List<ApplicationDto> findApplicationsDtoEmployeeByDepartment(Integer departmentId, UUID employeeId);
    List<ApplicationDto> findVacationPlansDtoEmployeeByDepartment(Integer departmentId, UUID employeeId);
    List<ApplicationDto> findVacationPlansDtoEmployeeByDepartment(UUID employeeId, boolean vacationPlan);
    void saveApplicationDto(ApplicationDto applicationDto);
    Application getApplication(ApplicationDto applicationDto);
    ApplicationDto getApplicationDto(Application application);
}
