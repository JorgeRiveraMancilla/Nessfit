package cl.nessfit.web.service;

import cl.nessfit.web.model.DateRequest;
import cl.nessfit.web.repository.DateRequestRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DateRequestServiceImpl implements DateRequestServiceInterface {
    @Autowired
    private DateRequestRepositoryInterface dateRequestRepository;

    /**
     * Return a list with all date requests by request id.
     * @param id Request id.
     * @return List with all date requests.
     */
    @Override
    public List<DateRequest> findDateRequestsByRequestId(int id) {
        return dateRequestRepository.findDateRequestsByRequestId(id);
    }

    /**
     * Saves the dateRequest in the database.
     * @param dateRequest DateRequest to save.
     */
    @Override
    public void save(DateRequest dateRequest) {
        dateRequestRepository.save(dateRequest);
    }
}