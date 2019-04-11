package lewandowski.demo.Service;

import lewandowski.demo.DAO.ApplicationRepository;
import lewandowski.demo.DAO.ApplicationStatusRepository;
import lewandowski.demo.DTO.ApplicationDto;
import lewandowski.demo.Model.Application;
import lewandowski.demo.Model.ApplicationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private VacationTypeService vacationTypeService;
    @Autowired
    private ApplicationStatusService applicationStatusService;

    /*Entity method*/

    @Override
    @PostAuthorize("returnObject.employee.email == authentication.name or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public Application findApplicationById(UUID id) {
        return applicationRepository.findById(id);
    }

    @Override
    public List<Application> findAllApplications() {
        return applicationRepository.findAll();
    }

    @Override
    public List<Application> findApplicationsByEmployee_IdAndVacationPlanOrderByDateOfAdditionDesc(UUID employeeId, boolean vacationPlan) {
        return applicationRepository.findApplicationsByEmployee_IdAndVacationPlanOrderByDateOfAdditionDesc(employeeId, vacationPlan);
    }

    @Override
    public List<Application> findAllApplicationsAdmin(UUID employeeId) {
        return applicationRepository.findAllApplicationsAdmin(employeeId);
    }

    @Override
    public List<Application> findApplicationsEmployeeByDepartment(Integer departmentId, UUID employeeId) {
        return applicationRepository.findApplicationsEmployeeByDepartment(departmentId, employeeId);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteApplicationById(UUID applicationId) {
        applicationRepository.deleteApplicationById(applicationId);
    }


    @Override
    public void deleteApplicationByEmployeeId(UUID employeeId) {
        applicationRepository.deleteApplicationByEmployee_Id(employeeId);
    }

    @Override
    @PreAuthorize ("#application.employee.email == authentication.name")
    public void deleteApplication(Application application) {
        applicationRepository.delete(application);
    }

    @Override
    public void updateStatusApplication(Integer applicationStatus,UUID id) {
        applicationRepository.updateStatusApplication(applicationStatus,id);
    }

    @Override
    public void saveApplication(Application application) {
        Date date = new Date();
        application.setDateOfAddition(date);
        applicationRepository.save(application);
    }

    /*DTO method*/

    @Override
    public void saveApplicationDto(ApplicationDto applicationDto) {
        Application application = Application.builder()
                .dateOfAddition(applicationDto.getDateOfAddition())
                .startOfVacation(applicationDto.getStartOfVacation())
                .endOfVacation(applicationDto.getEndOfVacation())
                .vacationDays(applicationDto.getVacationDays())
                .vacationPlan(applicationDto.isVacationPlan())
                .applicationStatus(applicationStatusService.getApplicationStatus(applicationDto.getApplicationStatusDto()))
                .vacationType(vacationTypeService.getVacationType(applicationDto.getVacationTypeDto()))
                .replacement(applicationDto.getReplacement())
                .description(applicationDto.getDescription())
                .employee(employeeService.getEmployee(applicationDto.getEmployeeDto()))
                .build();

        Date date = new Date();
        application.setDateOfAddition(date);
        applicationRepository.save(application);
    }

    @Override
    @PostAuthorize("returnObject.employeeDto.email == authentication.name or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ApplicationDto findApplicationDtoById(UUID id) {
        if (!id.equals("")) {
            Application application = applicationRepository.findById(id);
            if (application != null) {
                return getApplicationDto(application);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public List<ApplicationDto> findAllApplicationsDto() {
        return findAllApplications(applicationRepository.findAll());
    }
    @Override
    public List<ApplicationDto> findApplicationsByEmployee_IdAndVacationPlanOrderByDateOfAdditionDescDto(UUID employeeId, boolean vacationPlan) {
        return findAllApplications(applicationRepository.findApplicationsByEmployee_IdAndVacationPlanOrderByDateOfAdditionDesc(employeeId, vacationPlan));
    }
    @Override
    public List<ApplicationDto> findAllApplicationsDtoAdmin(UUID employeeId) {
        return findAllApplications(applicationRepository.findAllApplicationsAdmin(employeeId));
    }
    @Override
    public List<ApplicationDto> findAllVacationPlansDtoAdmin(UUID employeeId) {
        return findAllApplications(applicationRepository.findAllVacationPlansAdmin(employeeId));
    }

    @Override
    public List<ApplicationDto> findApplicationsDtoEmployeeByDepartment(Integer departmentId, UUID employeeId) {
        return findAllApplications(applicationRepository.findApplicationsEmployeeByDepartment(departmentId, employeeId));
    }

    @Override
    public List<ApplicationDto> findVacationPlansDtoEmployeeByDepartment(Integer departmentId, UUID employeeId) {
        return findAllApplications(applicationRepository.findVacationPlansEmployeeByDepartment(departmentId, employeeId));
    }
    @Override
    public List<ApplicationDto> findVacationPlansDtoEmployeeByDepartment(UUID employeeId, boolean vacationPlan) {
        return findAllApplications(applicationRepository.findApplicationsByEmployee_IdAndVacationPlan(employeeId, vacationPlan));
    }

    public List<ApplicationDto> findAllApplications(List<Application> applicationsList) {
        List<ApplicationDto> applicationDtoList = new ArrayList<>();
        for (Application application : applicationsList) {
            ApplicationDto applicationDto = getApplicationDto(application);
            applicationDtoList.add(applicationDto);
        }
        return applicationDtoList;
    }

    public Application getApplication(ApplicationDto applicationDto) {
        return Application.builder()
                .id(applicationDto.getId())
                .dateOfAddition(applicationDto.getDateOfAddition())
                .startOfVacation(applicationDto.getStartOfVacation())
                .endOfVacation(applicationDto.getEndOfVacation())
                .vacationDays(applicationDto.getVacationDays())
                .vacationPlan(applicationDto.isVacationPlan())
                .applicationStatus(applicationStatusService.getApplicationStatus(applicationDto.getApplicationStatusDto()))
                .vacationType(vacationTypeService.getVacationType(applicationDto.getVacationTypeDto()))
                .replacement(applicationDto.getReplacement())
                .description(applicationDto.getDescription())
                .employee(employeeService.getEmployee(applicationDto.getEmployeeDto()))
                .nrStatus(applicationDto.getNrStatus())
                .build();
    }
    public ApplicationDto getApplicationDto(Application application) {
        return ApplicationDto.builder()
                .id(application.getId())
                .dateOfAddition(application.getDateOfAddition())
                .startOfVacation(application.getStartOfVacation())
                .endOfVacation(application.getEndOfVacation())
                .vacationDays(application.getVacationDays())
                .vacationPlan(application.isVacationPlan())
                .applicationStatusDto(applicationStatusService.getApplicationStatusDto(application.getApplicationStatus()))
                .vacationTypeDto(vacationTypeService.getVacationTypeDto(application.getVacationType()))
                .replacement(application.getReplacement())
                .description(application.getDescription())
                .employeeDto(employeeService.getEmployeeDto(application.getEmployee()))
                .nrStatus(application.getNrStatus())
                .build();
    }
}
