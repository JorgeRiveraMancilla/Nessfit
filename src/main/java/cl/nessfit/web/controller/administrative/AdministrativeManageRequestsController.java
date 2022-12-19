package cl.nessfit.web.controller.administrative;

import cl.nessfit.web.model.Request;
import cl.nessfit.web.service.RequestServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequestMapping("/administrative")
public class AdministrativeManageRequestsController {

    @Autowired
    private RequestServiceInterface requestService;
    /**
     * Get data from manage-request.html.
     * @param model Is the application's dynamic data structure.
     * @return Manage requests view.
     */
    @GetMapping("/manage-request")
    public String manageRequests(Model model) {
        List<Request> requests = requestService.getRequestsFilter();
        if (!requests.isEmpty()) {
            model.addAttribute("requests", requests);
        }
        return "administrative/manage-request";
    }

    /**
     * Receives data from manage-request.html.
     * @param model Is the application's dynamic data structure.
     * @param name Request name.
     * @return Manage requests page.
     */
    @PostMapping("/manage-request")
    public String manageRequests(Model model, @RequestParam("name") String name) {
        return "administrative/manage-request";
    }
}
