package cl.nessfit.web.service;

import cl.nessfit.web.model.Request;
import cl.nessfit.web.repository.RequestRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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
        return requestRepository.findRequestsByInstallationName(installationName);
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

    /**
     * Saves the request in the database.
     * @param request Request to save.
     */
    @Override
    public void save(Request request) {
        requestRepository.save(request);
    }

    @Override
    public List<Request> getRequests() {
        return requestRepository.findAll();
    }

    @Override
    public List<Request> searchByDate(LocalDate register) {
        return requestRepository.findRequestsByDateRequests(register);
    }
}