package cl.nessfit.web.util;

import cl.nessfit.web.model.DateRequest;
import cl.nessfit.web.service.InstallationServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class RequestValidation {
    @Autowired
    private InstallationServiceInterface installationService;

    private List<DateRequest> dateRequest;
    private String days;

    public RequestValidation(String days) {
        this.dateRequest = new ArrayList<>();
        this.days = days;
    }

    public String getDaysMessage() {
        if (days.equals("")) {
            return " Debe seleccionar al menos un día";
        }

        String[] listDays = this.days.split(",");
        for (String day : listDays) {
            System.out.println("DAY: " + day);
        }
        return null;
    }

    public List<DateRequest> getDateRequest() {
        return this.dateRequest;
    }
}