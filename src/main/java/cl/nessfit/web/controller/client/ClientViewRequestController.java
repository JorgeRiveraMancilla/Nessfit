package cl.nessfit.web.controller.client;

import cl.nessfit.web.model.Request;
import cl.nessfit.web.service.RequestServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientViewRequestController {
    @Autowired
    private RequestServiceInterface requestService;

    /**
     * Get data from view-request.html.
     * @param model Is the application's dynamic data structure.
     * @return View request view.
     */
    @GetMapping("/view-request")
    public String viewRequest(Model model) {
        String rut = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Request> requests = requestService.getRequestsByUser(rut);
        if (!requests.isEmpty()) {
            model.addAttribute("requests", requests);
        }
        return "client/view-request";
    }

    /**
     * Receives data from view-request.html.
     * @param model Is the application's dynamic data structure.
     * @param name Request name.
     * @return View request page.
     */
    @PostMapping("view-request")
    public String viewRequest(Model model, @RequestParam("name") String name) {
        List<Request> requests = requestService.getRequestsByInstallationNameLike(name);
        if (!requests.isEmpty()) {
            model.addAttribute("requests", requests);
        }
        return "client/view-request";
    }
}