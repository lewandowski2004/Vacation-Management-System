package lewandowski.demo.Service;

import lewandowski.demo.DAO.DepartmentRepository;
import lewandowski.demo.DTO.DepartmentDto;
import lewandowski.demo.Model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private PositionService positionService;
    @Autowired
    private EmployeeService employeeService;

    /*Entity method*/

    @Override
    public Department findDepartmentById(int id) {
        return departmentRepository.findById(id);
    }

    @Override
    public List<Department> findAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public void saveDepartment(Department department) {
        departmentRepository.save(department);
    }

    @Override
    public Department findDepartmentByName(String name) {
        return departmentRepository.findByName(name);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteDepartmentById(int departmentId) {
        departmentRepository.deleteDepartmentById(departmentId);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateDepartmentName(String newName, Integer id) {
        departmentRepository.updateDepartmentName(newName,id);
    }

    /*DTO method*/

    @Override
    public void saveDepartment(DepartmentDto departmentDto) {
        Department department = Department.builder()
                .name(departmentDto.getName())
                .build();
        departmentRepository.save(department);
    }

    @Override
    public DepartmentDto findDepartmentDtoById(int id) {
        Department department = departmentRepository.findById(id);
        if (department != null) {
            return getDepartmentDto(department);
        } else {
            return null;
        }
    }
    @Override
    public DepartmentDto findDepartmentDtoByName(String name) {
        Department department = departmentRepository.findByName(name);
        if (department != null) {
            return getDepartmentDto(department);
        } else {
            return null;
        }
    }

    @Override
    public List<DepartmentDto> findAllDepartamentDto() {
        return findAllDepartamentDto(departmentRepository.findAll());
    }

    public List<DepartmentDto> findAllDepartamentDto(List<Department> departamentsList) {
        List<DepartmentDto> DepartamentDtoList = new ArrayList<>();
        for (Department department : departamentsList) {
            DepartmentDto departmentDto = getDepartmentDto(department);
            DepartamentDtoList.add(departmentDto);
        }
        return DepartamentDtoList;
    }
    public Department getDepartment(DepartmentDto departmentDto) {
        if(departmentDto != null) {
            return Department.builder()
                    .id(departmentDto.getId())
                    .name(departmentDto.getName())
                    .build();
        }else {
            return null;
        }
    }
    public DepartmentDto getDepartmentDto(Department department) {
        if(department != null) {
            return DepartmentDto.builder()
                    .id(department.getId())
                    .name(department.getName())
                    .positionsDto(positionService.getPositionsDtoWithoutDepartament(department.getPositions()))
                    .build();
        }else {
            return null;
        }
    }
    public DepartmentDto getDepartmentDtoWithoutPosition(Department department) {
        if(department != null) {
            return DepartmentDto.builder()
                    .id(department.getId())
                    .name(department.getName())
                   // .positionsDto(positionService.getPositionsDto(department.getPositions()))
                    .build();
        }else {
            return null;
        }
    }
}
