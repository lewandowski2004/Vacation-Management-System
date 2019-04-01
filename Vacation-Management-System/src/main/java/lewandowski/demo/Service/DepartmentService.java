package lewandowski.demo.Service;

import lewandowski.demo.DTO.DepartmentDto;
import lewandowski.demo.Model.Department;

import java.util.List;

public interface DepartmentService {

    /*Entity method*/

    Department findDepartmentById(int id);
    List<Department> findAllDepartments();
    void saveDepartment(Department department);
    Department findDepartmentByName(String name);
    void deleteDepartmentById(int departmentId);
    void updateDepartmentName(String newName, Integer id);

    /*DTO method*/

    DepartmentDto findDepartmentDtoById(int id);
    List<DepartmentDto> findAllDepartamentDto();
    void saveDepartment(DepartmentDto departmentDto);
    DepartmentDto findDepartmentDtoByName(String name);
    Department getDepartment(DepartmentDto departmentDto);
    DepartmentDto getDepartmentDto(Department department);
    DepartmentDto getDepartmentDtoWithoutPosition(Department department);
}
