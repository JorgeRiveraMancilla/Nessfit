package cl.nessfit.web.service;

import cl.nessfit.web.model.DateRequest;
import cl.nessfit.web.repository.DateRequestRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;

public class DateRequestServiceImpl implements DateRequestServiceInterface {
    @Autowired
    private DateRequestRepositoryInterface dateRequestRepository;

    @Override
    public void save(DateRequest dateRequest) {
        dateRequestRepository.save(dateRequest);
    }
}