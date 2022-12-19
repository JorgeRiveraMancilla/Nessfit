package cl.nessfit.web.controller.administrative;

import cl.nessfit.web.model.DateRequest;
import cl.nessfit.web.model.Installation;
import cl.nessfit.web.model.Request;
import cl.nessfit.web.service.RequestServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/administrative")
public class AdministrativeRequestDetailsController {

    @Autowired
    private RequestServiceInterface requestService;

    /**
     * Get data from request-details.html.
     * @param model Is the application's dynamic data structure.
     * @param id Request id.
     * @return Request details page view.
     */
    @GetMapping("/request-details/{id}")
    public String requestDetails(Model model, @PathVariable int id){
        Request request = requestService.getRequestById(id);
        Set<DateRequest> dateRequests = request.getDateRequests();
        model.addAttribute("request", request);
        model.addAttribute("dates", dateRequests);
        return "administrative/request-details";
    }

    /**
     * Receives data request-details.html.
     * @param result Result valid.
     * @param id Request id.
     * @return Return user to manage-request page.
     */
    @PostMapping("/request-details")
    public String requestDetails(@RequestParam("result") String result, @RequestParam("id") String id){
        Request request = requestService.getRequestById(Integer.parseInt(id));
        request.setStatus(Integer.parseInt(result));
        requestService.save(request);
        return "redirect:/administrative/manage-request";
    }
}