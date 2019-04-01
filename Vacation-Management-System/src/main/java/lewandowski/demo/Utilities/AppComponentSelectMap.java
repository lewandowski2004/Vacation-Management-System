package lewandowski.demo.Utilities;

import lewandowski.demo.DTO.*;
import lewandowski.demo.Model.*;
import lewandowski.demo.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class AppComponentSelectMap {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private VacationTypeService vacationTypeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private VacationBalanceService vacationBalanceService;


    @Autowired
    private ApplicationStatusService applicationStatusService;


    public List<Employee> getAllEmployees() {
        List<Employee> employeesList = employeeService.findAllEmployeesList();
        for (Employee employees : employeesList) {
            int numerRoli = employees.getRoles().iterator().next().getId();
            employees.setNrRoli(numerRoli);
        }
        return employeesList;
    }
    public List<EmployeeDto> getAllEmployeesWithNrRoleDto() {
        List<EmployeeDto> employeesList = employeeService.findAllEmployeesDto();
        for (EmployeeDto employeesDto : employeesList) {
            int numerRoli = employeesDto.getRolesDto().iterator().next().getId();
            employeesDto.setNrRoli(numerRoli);
        }
        return employeesList;
    }


    public Map<Integer, String> prepareDepartmentMap() {
        Map<Integer, String> departmentMap = new HashMap<Integer, String>();
        List<Department> departmentList = departmentService.findAllDepartments();
        for( Department department : departmentList){
            departmentMap.put(department.getId(),department.getName());
        }
        return departmentMap;
    }



    public Map<Integer, String> prepareDepartmentDtoMap() {
        Map<Integer, String> departamentDtoMap = new HashMap<Integer, String>();
        List<DepartmentDto> departmentList = departmentService.findAllDepartamentDto();
        for( DepartmentDto departmentDto : departmentList ){
            departamentDtoMap.put(departmentDto.getId(),departmentDto.getName());
        }
        return departamentDtoMap;
    }

    public Map<Integer, String> preparePositionMapByDepartmentId(int departmentId) {
        Map<Integer, String> positiontMapByDepartmentId = new HashMap<Integer, String>();
        List<Position> positionListById = positionService.findAllPositionsByDepartmentId(departmentId);
        for( Position position : positionListById){
            positiontMapByDepartmentId.put(position.getId(),position.getName());
        }
        return positiontMapByDepartmentId;
    }

    public Map<Integer, String> preparePositionDtoMapByDepartmentId(int departmentId) {
        Map<Integer, String> positionDtoMapByDepartmentId = new HashMap<Integer, String>();
        List<PositionDto> positionDtoListById = positionService.findAllPositionsDtoByDepartmentId(departmentId);
        for( PositionDto positionDto : positionDtoListById){
            positionDtoMapByDepartmentId.put(positionDto.getId(),positionDto.getName());
        }
        return positionDtoMapByDepartmentId;
    }

    public Map<Integer, String> prepareRoleMap() {
        Map<Integer, String> roleMap = new HashMap<Integer, String>();
        roleMap.put(2,"Administrator" );
        roleMap.put(4,"Kierownik");
        roleMap.put(6,"Pracownik");
        return roleMap;
    }

    public Map<Integer, String> prepareStatusMapManager() {
        Map<Integer, String> roleMap = new HashMap<Integer, String>();
        roleMap.put(2,"Zaakceptowany przez kierownika");
        roleMap.put(4,"Odrzucony przez kierownika");
        return roleMap;
    }

    public Map<Integer, String> prepareStatusMapAdmin() {
        Map<Integer, String> roleMap = new HashMap<Integer, String>();
        roleMap.put(3,"Zaakceptowany przez dział kadr");
        roleMap.put(5,"Odrzucony przez Dział kadr");
        return roleMap;
    }

    public Map<Integer, String> prepareApplicationStatusMap( ) {
        Map<Integer, String> applicationStatusMapId = new HashMap<Integer, String>();
        List<ApplicationStatus> applicationStatusListById = applicationStatusService.findAllApplicationsStatus();
        for( ApplicationStatus applicationStatus : applicationStatusListById){
            applicationStatusMapId.put(applicationStatus.getId(),applicationStatus.getStatus());
        }
        return applicationStatusMapId;
    }

    public Map<Integer, String> prepareVacationTypeMap() {
        Map<Integer, String> vacationTypetMap = new HashMap<Integer, String>();
        List<VacationType> vacationTypetList = vacationTypeService.findAllVacationsType();
        for( VacationType vacationType : vacationTypetList ){
            vacationTypetMap.put(vacationType.getId(),vacationType.getType());
        }
        return vacationTypetMap;
    }
    public Map<Integer, String> prepareVacationTypeDtoMap() {
        Map<Integer, String> vacationTypeDtoMap = new HashMap<Integer, String>();
        List<VacationTypeDto> vacationTypeDtoList = vacationTypeService.findAllVacationsTypeDto();
        for( VacationTypeDto vacationTypeDto : vacationTypeDtoList ){
            vacationTypeDtoMap.put(vacationTypeDto.getId(),vacationTypeDto.getType());
        }
        return vacationTypeDtoMap;
    }

    public Date DateFormat(Date date) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        String pattern = "yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String stringDate = simpleDateFormat.format(date);
        Date dateAfterFormat = format.parse(stringDate);
        return dateAfterFormat;
    }
}
