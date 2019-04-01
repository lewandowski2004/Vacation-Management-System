package lewandowski.demo.DAO;

import lewandowski.demo.Model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Integer> {

    Department findById(int id);
    List<Department> findAll();
    Department findByName(String name);
    Department save(Department department);
    void deleteDepartmentById(int departmentId);

    @Modifying
    @Query("UPDATE Department d SET d.name = :newName WHERE d.id= :id")
    void updateDepartmentName(@Param("newName") String newName, @Param("id") Integer id);
}
