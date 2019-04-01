package lewandowski.demo.DAO;

import lewandowski.demo.Model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

    Position findById(int id);
    Position findByName(String name);
    Position save(Position position);
    List<Position> findAll();
    List<Position> findPositionsByDepartment_Id(int departmentId);
    void deletePositionsById(int positionId);
    void deletePositionsByDepartment_Id(int departmentId);

    @Modifying
    @Query(value = "UPDATE position p SET p.name = :newName, p.department_id = :newDepartmentId WHERE p.position_id = :id", nativeQuery = true)
    void updatePosition(@Param("newName") String newName, @Param("newDepartmentId") Integer newDepartmentId,
                                             @Param("id") Integer id);
}
