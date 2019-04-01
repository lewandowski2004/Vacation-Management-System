package lewandowski.demo.Service;

import lewandowski.demo.DTO.PositionDto;
import lewandowski.demo.Model.Position;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface PositionService {

    /*Entity method*/

    Position findPositionById(int id);
    List<Position> findAllPositions();
    void savePosition(Position position);
    Position findPositionByName(String name);
    List<Position> findAllPositionsByDepartmentId(int departmentId);
    void deletePositionById(int positionId);
    void deletePositionByDepartmentId(int departmentId);
    void updatePosition(String newName, Integer newDepartmentId, Integer id);

    /*Dto method*/

    PositionDto findPositionDtoById(int id);
    PositionDto findPositionDtoByName(String name);
    void savePositionDto(PositionDto positionDto);
    List<PositionDto> findAllPositionDto();
    List<PositionDto> findAllPositionsDtoByDepartmentId(int departmentId);
    Set<Position> getPositions(Set<PositionDto> positionsDto);
    Set<Position> getPositionsWithoutDepartment(Set<PositionDto> positionsDto);
    Set<PositionDto> getPositionsDto(Set<Position> positions);
    Set<PositionDto> getPositionsDtoWithoutDepartament(Set<Position> positions);
    Position getPosition(PositionDto positionDto);
    PositionDto getPositionDto(Position position);
    PositionDto getPositionDtoWithoutDepartment(Position position);
}
