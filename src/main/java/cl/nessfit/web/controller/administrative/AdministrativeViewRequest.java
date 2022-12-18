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

import javax.swing.text.DateFormatter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static java.time.LocalDateTime.parse;

@Controller
@RequestMapping("/administrative")
public class AdministrativeViewRequest {
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
        if (requests.isEmpty()) {
            model.addAttribute("requests", null);
        } else {
            model.addAttribute("requests", requests);
        }
        return "administrative/view-request";
    }

    @PostMapping("/view-request")
    public String viewRequests(Model model, @RequestParam("register") String register)
    {
        LocalDate date = LocalDate.parse(register, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        model.addAttribute("requests", requestService.searchByDate(date));
        System.out.println("Hola");
        return "administrative/view-request";
    }
}
