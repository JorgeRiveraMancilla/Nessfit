package cl.nessfit.web.controller.client;

import cl.nessfit.web.model.DateRequest;
import cl.nessfit.web.repository.DateRequestRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientViewDetailRequestController {
    @Autowired
    private DateRequestRepositoryInterface dateRequestRepository;

    @GetMapping("/view-request/{id}")
    public String viewDetailRequest(Model model, @PathVariable int id) {
        List<DateRequest> dateRequests = dateRequestRepository.findDateRequestsByRequestId(id);
        model.addAttribute("dateRequests", dateRequests);
        return "client/view-detail-request";
    }
}