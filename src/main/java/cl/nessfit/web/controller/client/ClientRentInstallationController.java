package cl.nessfit.web.controller.client;

import cl.nessfit.web.model.DateRequest;
import cl.nessfit.web.model.Installation;
import cl.nessfit.web.model.Request;
import cl.nessfit.web.model.User;
import cl.nessfit.web.repository.DateRequestRepositoryInterface;
import cl.nessfit.web.service.DateRequestServiceInterface;
import cl.nessfit.web.service.InstallationServiceInterface;
import cl.nessfit.web.service.RequestServiceInterface;
import cl.nessfit.web.service.UserServiceInterface;
import cl.nessfit.web.util.RequestValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.util.*;

@Controller
@RequestMapping ("/client")
public class ClientRentInstallationController {
    @Autowired
    private InstallationServiceInterface installationService;
    @Autowired
    private RequestServiceInterface requestService;
    @Autowired
    private UserServiceInterface userService;

    @GetMapping ("/view-installation")
    public String viewInstallation(Model model) {
        List<Installation> installations = installationService.getInstallationsBy(1);
        if (installations.isEmpty()) {
            model.addAttribute("installations", null);
        } else {
            model.addAttribute("installations", installations);
        }
        return "client/view-installation";
    }

    @PostMapping("/view-installation")
    public String viewInstallation(Model model, @RequestParam ("name") String name) {
        model.addAttribute("installations", installationService.searchByName(name));
        return "client/view-installation";
    }

    @GetMapping ("/rent-installation/{name}")
    public String rentInstallation(Model model, @PathVariable String name) {
        Installation installation = installationService.searchByName(name);
        List<Request> requests = requestService.getRequestsBy(name);
        model.addAttribute("installation", installation);
        model.addAttribute("requests", requests);
        return "client/rent-installation";
    }

    @PostMapping ("/rent-installation")
    public String rentInstallation(@RequestParam Map<String, String> allParams, Model model) throws ParseException {
        String name = allParams.get("name");
        String days = allParams.get("days");
        Installation installation = installationService.searchByName(name);
        RequestValidation requestValidation = new RequestValidation(days);
        String daysMessage = requestValidation.getDaysMessage();
        if (daysMessage != null) {
            List<Request> requests = requestService.getRequestsBy(name);
            model.addAttribute("installation", installation);
            model.addAttribute("requests", requests);
            model.addAttribute("daysMessage", daysMessage);
            return "client/rent-installation";
        }
        User user = userService.searchByRut(SecurityContextHolder.getContext().getAuthentication().getName());
        Request request = new Request();
        Set<DateRequest> dateRequests = new HashSet<>();
        for (Date date : requestValidation.getListDates()) {
            DateRequest dateRequest = new DateRequest();
            dateRequest.setRequest(request);
            dateRequest.setDate(date);
            dateRequests.add(dateRequest);
        }
        request.setStatus(1);
        request.setRegister(new Date());
        request.setUser(user);
        request.setInstallation(installation);
        request.setDateRequests(dateRequests);
        requestService.save(request);
        return "redirect:/client/view-installation";
    }
}