package cl.nessfit.web.repository;

import cl.nessfit.web.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestRepositoryInterface extends JpaRepository<Request, Integer> {

    /**
     * Select * from requests r where r.id = id.
     * @param id Request id.
     * @return Request with the same id.
     */
    Request findRequestById(int id);

    /**
     * Select * from requests.
     * @return All requests from the database.
     */
    List<Request> findAll();

    /**
     * Finds all requests beginning with the given word.
     * @param name Word delivered.
     * @return All requests beginning with the given word.
     */
    @Query(value = "SELECT r.id, r.price, r.quantity, r.register, r.status, r.id_installation, r.rut_user FROM requests r, installations i WHERE r.id_installation = i.id AND i.name like ?1%", nativeQuery = true)
    List<Request> findRequestsByInstallationNameLike(String name);

    /**
     * Find all requests that are in 'Pendiente' status.
     * @return All requests that are in 'Pendiente' status.
     */
    @Query(value = "SELECT r.id, r.price, r.quantity, r.register, r.status, r.id_installation, r.rut_user FROM requests r WHERE r.status = 1 ORDER BY r.register ASC", nativeQuery = true)
    List<Request> findRequestsFilter();

    /**
     * Select * from requests r where r.rut_user = rut.
     * @param rut RUT from user.
     * @return All requests from user.
     */
    List<Request> findAllByUser_Rut(String rut);
}