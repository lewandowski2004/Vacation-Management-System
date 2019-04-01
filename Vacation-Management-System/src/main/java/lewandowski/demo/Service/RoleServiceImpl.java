package lewandowski.demo.Service;

import lewandowski.demo.DAO.RoleRepository;
import lewandowski.demo.DTO.RoleDto;
import lewandowski.demo.Model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    /*Entity method*/

    @Override
    public List<Role> findAllEmployees() {
        return roleRepository.findAll();
    }

    /*Dto method*/

    public Role getRole(RoleDto roleDto) {
        return Role.builder()
                .id(roleDto.getId())
                .role(roleDto.getRole())
                .build();
    }
    public RoleDto getRoleDto(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .role(role.getRole())
                .build();
    }

    public Set<Role> getRoles(Set<RoleDto> roleDtos) {
        Set<Role> roles = new HashSet<>();
        for (RoleDto roleDto : roleDtos) {
            roles.add(getRole(roleDto));
        }
        return roles;
    }

    public Set<RoleDto> getRolesDto(Set<Role> roleList) {
        Set<RoleDto> roles = new HashSet<>();
        for (Role role : roleList) {
            roles.add(getRoleDto(role));
        }
        return roles;
    }
}
