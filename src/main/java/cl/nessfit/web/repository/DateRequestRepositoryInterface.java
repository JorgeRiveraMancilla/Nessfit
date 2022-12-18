package cl.nessfit.web.repository;

import cl.nessfit.web.model.DateRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DateRequestRepositoryInterface extends JpaRepository<DateRequest, Integer> {
    List<DateRequest> findDateRequestsByRequestId(int id);
}