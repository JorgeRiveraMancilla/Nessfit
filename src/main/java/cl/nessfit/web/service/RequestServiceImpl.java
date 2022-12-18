package cl.nessfit.web.service;

import cl.nessfit.web.model.Request;
import cl.nessfit.web.repository.RequestRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestServiceInterface {
    @Autowired
    private RequestRepositoryInterface requestRepository;

    /**
     * Returns a list with all requests that match the installation name in the database.
     * @param installationName Request installation name.
     * @return List with all requests that match the installation name.
     */
    @Override
    public List<Request> getRequestsBy(String installationName) {
        return requestRepository.findRequestsByInstallation_Name(installationName);
    }

    @Override
    public Request getRequestById(int id) {
        return requestRepository.findRequestById(id);
    }

    /**
     * Saves the request in the database.
     * @param request Request to save.
     */
    @Override
    public void save(Request request) {
        requestRepository.save(request);
    }

    @Override
    public List<Request> getRequests() { return requestRepository.findAll(); }
}