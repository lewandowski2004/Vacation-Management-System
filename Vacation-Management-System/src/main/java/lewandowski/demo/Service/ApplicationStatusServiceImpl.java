package lewandowski.demo.Service;

import lewandowski.demo.DAO.ApplicationStatusRepository;
import lewandowski.demo.DTO.ApplicationStatusDto;
import lewandowski.demo.Model.Application;
import lewandowski.demo.Model.ApplicationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationStatusServiceImpl implements ApplicationStatusService {

    @Autowired
    private ApplicationStatusRepository applicationStatusRepository;
    @Autowired
    private ApplicationService applicationService;

    /*Entity method*/

    @Override
    public ApplicationStatus findApplicationStatusById(int id) {
        return applicationStatusRepository.findById(id);
    }

    @Override
    public List<ApplicationStatus> findAllApplicationsStatus() {
        return applicationStatusRepository.findAll();
    }

    @Override
    public ApplicationStatus findApplicationStatusByStatus(String status) {
        return applicationStatusRepository.findByStatus(status);
    }

    /*DTO method*/

    @Override
    public ApplicationStatusDto findApplicationStatusDtoById(int id) {
            ApplicationStatus applicationStatus = applicationStatusRepository.findById(id);
            if (applicationStatus != null) {
                return getApplicationStatusDto(applicationStatus);
            } else {
                return null;
            }
    }

    @Override
    public List<ApplicationStatusDto> findAllApplicationsStatusDto() {
        return findAllApplicationstatusDto(applicationStatusRepository.findAll());
    }

    @Override
    public ApplicationStatusDto findApplicationStatusDtoByStatus(String status) {
        ApplicationStatus applicationStatus = applicationStatusRepository.findByStatus(status);
        if (applicationStatus != null) {
            return getApplicationStatusDto(applicationStatus);
        } else {
            return null;
        }
    }
    public List<ApplicationStatusDto> findAllApplicationstatusDto(List<ApplicationStatus> applicationsStatusList) {
        List<ApplicationStatusDto> applicationStatusDtoList = new ArrayList<>();
        for (ApplicationStatus applicationStatus : applicationsStatusList) {
            ApplicationStatusDto applicationStatusDto = getApplicationStatusDto(applicationStatus);
            applicationStatusDtoList.add(applicationStatusDto);
        }
        return applicationStatusDtoList;
    }
    public ApplicationStatus getApplicationStatus(ApplicationStatusDto applicationStatusDto) {
        return ApplicationStatus.builder()
                .id(applicationStatusDto.getId())
                .status(applicationStatusDto.getStatus())
                .build();
    }
    public ApplicationStatusDto getApplicationStatusDto(ApplicationStatus applicationStatus) {
        return ApplicationStatusDto.builder()
                .id(applicationStatus.getId())
                .status(applicationStatus.getStatus())
                .build();
    }
}
