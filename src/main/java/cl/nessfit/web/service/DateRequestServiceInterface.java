package cl.nessfit.web.service;

import cl.nessfit.web.model.DateRequest;

import java.util.List;

public interface DateRequestServiceInterface {

    /**
     * Saves the dateRequest in the database.
     * @param dateRequest DateRequest to save.
     */
    void save(DateRequest dateRequest);
}