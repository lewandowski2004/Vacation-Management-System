package lewandowski.demo.DAO;

import lewandowski.demo.Model.VacationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacationTypeRepository extends JpaRepository<VacationType, Integer> {

    List<VacationType> findAll();
    VacationType findById(int id);
}
