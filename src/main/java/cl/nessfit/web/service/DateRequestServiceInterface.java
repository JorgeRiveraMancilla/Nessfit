package cl.nessfit.web.service;

import cl.nessfit.web.model.DateRequest;

import java.util.List;

public interface DateRequestServiceInterface {

    /**
     * Return a list with all date requests by request id.
     * @param id Request id.
     * @return List with all date requests.
     */
    List<DateRequest> findDateRequestsByRequestId(int id);

    /**
     * Saves the dateRequest in the database.
     * @param dateRequest DateRequest to save.
     */
    void save(DateRequest dateRequest);
}