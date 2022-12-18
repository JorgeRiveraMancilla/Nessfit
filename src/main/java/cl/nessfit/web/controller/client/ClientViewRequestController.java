package cl.nessfit.web.controller.client;

import cl.nessfit.web.model.Request;
import cl.nessfit.web.service.RequestServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientViewRequestController {
    @Autowired
    private RequestServiceInterface requestService;

    @GetMapping("/view-request")
    public String viewInstallation(Model model) {
        String rut = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Request> requests = requestService.getRequestsByUser(rut);
        if (!requests.isEmpty()) {
            model.addAttribute("requests", requests);
        }
        return "client/view-request";
    }
}