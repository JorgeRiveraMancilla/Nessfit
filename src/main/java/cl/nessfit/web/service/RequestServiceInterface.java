package cl.nessfit.web.service;

import cl.nessfit.web.model.Request;
import java.util.List;

public interface RequestServiceInterface {

    /**
     * Returns a list with all requests that match the installation name in the database.
     * @param installationName Request installation name.
     * @return List with all requests that match the installation name.
     */
    List<Request> getRequestsBy(String installationName);

    /**
     * Saves the request in the database.
     * @param request Request to save.
     */
    void save(Request request);
}