package cl.nessfit.web.repository;

import cl.nessfit.web.model.DateRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DateRequestRepositoryInterface extends JpaRepository<DateRequest, Integer> {

    /**
     * Select * from date_requests d where d.id = id.
     * @param id Date request id.
     * @return All date requests with the same id.
     */
    List<DateRequest> findDateRequestsByRequestId(int id);
}