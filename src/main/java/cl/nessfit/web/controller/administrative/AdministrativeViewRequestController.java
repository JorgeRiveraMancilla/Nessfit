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
public class AdministrativeViewRequestController {
    @Autowired
    private RequestServiceInterface requestService;

    /**
     * Get data from view-request.html.
     * @param model Is the application's dynamic data structure.
     * @return View request view.
     */
    @GetMapping("/view-request")
    public String viewRequests(Model model) {
        List<Request> requests =  requestService.getRequests();
        if (!requests.isEmpty()) {
            model.addAttribute("requests", requests);
        }
        return "administrative/view-request";
    }
}
