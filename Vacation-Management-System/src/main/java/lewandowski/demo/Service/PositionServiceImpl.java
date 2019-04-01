package lewandowski.demo.Service;

import lewandowski.demo.DAO.PositionRepository;
import lewandowski.demo.DTO.PositionDto;
import lewandowski.demo.Model.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class PositionServiceImpl implements PositionService{

    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private DepartmentService departmentService;


    /*Entity method*/

    @Override
    public Position findPositionById(int id) {
        return positionRepository.findById(id);
    }

    @Override
    public List<Position> findAllPositions() {
        return positionRepository.findAll();
    }

    @Override
    public void savePosition(Position position) {
        positionRepository.save(position);
    }

    @Override
    public Position findPositionByName(String name) {
        return positionRepository.findByName(name);
    }

    @Override
    public List<Position> findAllPositionsByDepartmentId(int departmentId) {
        return positionRepository.findPositionsByDepartment_Id(departmentId);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deletePositionById(int positionId) {
        positionRepository.deletePositionsById(positionId);
    }

    @Override
    public void deletePositionByDepartmentId(int departmentId) {
        positionRepository.deletePositionsByDepartment_Id(departmentId);
    }

    @Override
    public void updatePosition(String newName, Integer newDepartmentId, Integer id) {
        positionRepository.updatePosition(newName,newDepartmentId,id);
    }

    /*Dto method*/

    @Override
    public PositionDto findPositionDtoById(int id) {
        Position position = positionRepository.findById(id);
        if (position != null) {
            return getPositionDto(position);
        } else {
            return null;
        }
    }

    @Override
    public PositionDto findPositionDtoByName(String name) {
        Position position = positionRepository.findByName(name);
        if (position != null) {
            return getPositionDto(position);
        } else {
            return null;
        }
    }

    @Override
    public List<PositionDto> findAllPositionsDtoByDepartmentId(int departmentId) {
        return findAllPositionDto(positionRepository.findPositionsByDepartment_Id(departmentId));
    }


    @Override
    public List<PositionDto> findAllPositionDto() {
        return findAllPositionDto(positionRepository.findAll());
    }


    public List<PositionDto> findAllPositionDto(List<Position> positionsList) {
        List<PositionDto> PositionDtoList = new ArrayList<>();
        for (Position position : positionsList) {
            PositionDto positionDto = getPositionDto(position);
            PositionDtoList.add(positionDto);
        }
        return PositionDtoList;
    }
    @Override
    public void savePositionDto(PositionDto positionDto) {
        Position position = Position.builder()
                .name(positionDto.getName())
                .department(departmentService.getDepartment(positionDto.getDepartmentDto()))
                .build();
        positionRepository.save(position);
    }
    public Position getPosition(PositionDto positionDto) {
        return Position.builder()
                .id(positionDto.getId())
                .name(positionDto.getName())
                .department(departmentService.getDepartment(positionDto.getDepartmentDto()))
                .nrDepartment(positionDto.getNrDepartment())
                .build();
    }
    public Position getPositionWithoutDepartment(PositionDto positionDto) {
        return Position.builder()
                .id(positionDto.getId())
                .name(positionDto.getName())
                //.department(departmentService.getDepartment(positionDto.getDepartmentDto()))
                .nrDepartment(positionDto.getNrDepartment())
                .build();
    }
    public PositionDto getPositionDto(Position position) {
        return PositionDto.builder()
                .id(position.getId())
                .name(position.getName())
                .departmentDto(departmentService.getDepartmentDtoWithoutPosition(position.getDepartment()))
                .nrDepartment(position.getNrDepartment())
                .build();
    }
    public PositionDto getPositionDtoWithoutDepartment(Position position) {
        return PositionDto.builder()
                .id(position.getId())
                .name(position.getName())
                //.departmentDto(departmentService.getDepartmentDto(position.getDepartment()))
                .nrDepartment(position.getNrDepartment())
                .build();
    }

    public Set<Position> getPositionsWithoutDepartment(Set<PositionDto> positionsDto) {
        Set<Position> positions = new HashSet<>();
        for (PositionDto positionDto : positionsDto) {
            positions.add(getPositionWithoutDepartment(positionDto));
        }
        return positions;
    }

    public Set<Position> getPositions(Set<PositionDto> positionsDto) {
        Set<Position> positions = new HashSet<>();
        for (PositionDto positionDto : positionsDto) {
            positions.add(getPosition(positionDto));
        }
        return positions;
    }


    public Set<PositionDto> getPositionsDto(Set<Position> positions) {
        Set<PositionDto> positionsDto = new HashSet<>();
        for (Position position : positions) {
            positionsDto.add(getPositionDto(position));
        }
        return positionsDto;
    }
    public Set<PositionDto> getPositionsDtoWithoutDepartament(Set<Position> positions) {
        Set<PositionDto> positionsDto = new HashSet<>();
        for (Position position : positions) {
            positionsDto.add(getPositionDtoWithoutDepartment(position));
        }
        return positionsDto;
    }
}
