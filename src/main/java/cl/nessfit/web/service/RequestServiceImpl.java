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
    public List<Request> getRequestsByInstallationNameLike(String installationName) {
        return requestRepository.findRequestsByInstallationNameLike(installationName);
    }

    /**
     * Returns a list with all requests from user.
     * @param rut RUT from user.
     * @return List with all requests from user.
     */
    @Override
    public List<Request> getRequestsByUser(String rut) {
        return requestRepository.findAllByUser_Rut(rut);
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

    @Override
    public List<Request> getRequestsFilter() {
        return requestRepository.findRequestsFilter();
    }
}