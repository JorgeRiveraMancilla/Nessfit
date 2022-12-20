package cl.nessfit.web.controller.client;

import cl.nessfit.web.model.DateRequest;
import cl.nessfit.web.model.Installation;
import cl.nessfit.web.model.Request;
import cl.nessfit.web.model.User;
import cl.nessfit.web.service.InstallationServiceInterface;
import cl.nessfit.web.service.RequestServiceInterface;
import cl.nessfit.web.service.UserServiceInterface;
import cl.nessfit.web.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDate;
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

    /**
     * Get data from view-installation.html.
     * @param model Is the application's dynamic data structure.
     * @return View installation view.
     */
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

    /**
     * Receives data from view-installation.html.
     * @param name Installation's name.
     * @param model Is the application's dynamic data structure.
     * @return View installation page.
     */
    @PostMapping("/view-installation")
    public String viewInstallation(Model model, @RequestParam ("name") String name) {
        model.addAttribute("installations", installationService.searchByName(name));
        return "client/view-installation";
    }

    /**
     * Get data from rent-installation.html.
     * @param model Is the application's dynamic data structure.
     * @param id Installation id to rent.
     * @return Rent installation view.
     */
    @GetMapping ("/rent-installation/{id}")
    public String rentInstallation(Model model, @PathVariable int id) {
        Installation installation = installationService.searchById(id);
        List<Request> requests = requestService.getRequestsByInstallationNameLike(installation.getName());
        model.addAttribute("installation", installation);
        model.addAttribute("requests", requests);
        return "client/rent-installation";
    }

    /**
     * Receives data from edit-profile.html.
     * @param allParams Return all params from the model.
     * @param model Is the application's dynamic data structure.
     * @return Redirect to view installation page.
     * @throws ParseException Signals that an error has been reached unexpectedly while parsing.
     */
    @PostMapping ("/rent-installation")
    public String rentInstallation(@RequestParam Map<String, String> allParams, Model model) throws ParseException {
        String name = allParams.get("name");
        String days = allParams.get("days");
        Installation installation = installationService.searchByName(name);

        String daysMessage = Validation.getDaysMessage(days);
        if (daysMessage != null) {
            List<Request> requests = requestService.getRequestsByInstallationNameLike(name);
            model.addAttribute("installation", installation);
            model.addAttribute("requests", requests);
            model.addAttribute("daysMessage", daysMessage);
            return "client/rent-installation";
        }
        User user = userService.searchByRut(SecurityContextHolder.getContext().getAuthentication().getName());
        Request request = new Request();
        Set<DateRequest> dateRequests = new HashSet<>();
        for (LocalDate date : Validation.getListDates()) {
            DateRequest dateRequest = new DateRequest();
            dateRequest.setRequest(request);
            dateRequest.setDate(date);
            dateRequests.add(dateRequest);
        }
        request.setStatus(1);
        request.setPrice(Long.parseLong(installation.getRentalCost()) * dateRequests.size());
        request.setQuantity(dateRequests.size());
        request.setRegister(LocalDate.now());
        request.setUser(user);
        request.setInstallation(installation);
        request.setDateRequests(dateRequests);
        requestService.save(request);
        return "redirect:/client/view-installation";
    }
}