package lewandowski.demo.DAO;

import lewandowski.demo.Model.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationStatusRepository extends JpaRepository<ApplicationStatus, Integer> {

    ApplicationStatus findById(int id);
    List<ApplicationStatus> findAll();
    ApplicationStatus findByStatus(String status);
}
