package cl.nessfit.web.controller.administrative;

import cl.nessfit.web.model.Request;
import cl.nessfit.web.service.RequestServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/administrative")
public class AdministrativeManageRequestController {
    @Autowired
    private RequestServiceInterface requestService;

    @GetMapping("/manage-request")
    public String manageRequests(Model model) {
        List<Request> requests = requestService.getRequestsFilter();
        if (!requests.isEmpty()) {
            model.addAttribute("requests", requests);
        }
        return "administrative/manage-request";
    }
}