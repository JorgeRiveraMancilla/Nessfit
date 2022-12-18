package cl.nessfit.web.service;

import cl.nessfit.web.model.Request;

import java.util.List;

public interface RequestServiceInterface {

    /**
     * Returns a list with all requests that match the installation name in the database.
     * @param installationName Request installation name.
     * @return List with all requests that match the installation name.
     */
    List<Request> getRequestsByInstallationNameLike(String installationName);

    /**
     * Returns a list with all requests from user.
     * @param rut RUT from user.
     * @return List with all requests from user.
     */
    List<Request> getRequestsByUser(String rut);

    /**
     * Saves the request in the database.
     * @param request Request to save.
     */
    void save(Request request);

    /**
     * Return a list with all requests from the database.
     * @return List with all requests.
     */
    List<Request> getRequests();

    /**
     * Given a date, returns all requests with that date data.
     * @param register Date to search in requests.
     * @return Request with that date data.
     */
    List<Request> searchByDate(LocalDate register);
}