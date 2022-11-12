package cl.nessfit.web.service;

import cl.nessfit.web.model.Request;
import java.util.List;

public interface RequestServiceInterface {
    List<Request> getRequestsBy(String installationName);

    void save(Request request);
}