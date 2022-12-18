package cl.nessfit.web.repository;

import cl.nessfit.web.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface RequestRepositoryInterface extends JpaRepository<Request, Integer> {

    /*
     * Select * form requests r where r.name = name.
     * @param name request name.
     * @return All requests with the same name.
     */
    @Query(value = "SELECT r.id, r.price, r.quantity, r.register, r.status, r.id_installation, r.rut_user FROM requests r, installations i WHERE r.id_installation = i.id AND i.name like ?1%", nativeQuery = true)
    List<Request> findRequestsByInstallationName(String name);

    /**
     * Select * from requests r where r.rut_user = rut.
     * @param rut RUT from user.
     * @return All requests from user.
     */
    List<Request> findAllByUser_Rut(String rut);
}