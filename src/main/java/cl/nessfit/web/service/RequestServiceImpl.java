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

    @Override
    public List<Request> getRequestsBy(String installationName) {
        return requestRepository.findRequestsByInstallation_Name(installationName);
    }

    @Override
    public void save(Request request) {
        requestRepository.save(request);
    }
}