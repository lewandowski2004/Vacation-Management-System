package lewandowski.demo.Service;

import lewandowski.demo.DTO.RoleDto;
import lewandowski.demo.Model.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public interface RoleService {

    /*Entity method*/

    List<Role> findAllEmployees();

    /*Dto method*/

    Role getRole(RoleDto roleDto);
    RoleDto getRoleDto(Role role);
    Set<Role> getRoles(Set<RoleDto> roleDtos);
    Set<RoleDto> getRolesDto(Set<Role> roleList);
}
