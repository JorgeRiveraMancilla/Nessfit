package cl.nessfit.web.repository;

import cl.nessfit.web.model.DateRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DateRequestRepositoryInterface extends JpaRepository<DateRequest, Integer> {
}