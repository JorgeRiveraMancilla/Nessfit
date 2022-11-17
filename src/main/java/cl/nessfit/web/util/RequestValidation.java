package cl.nessfit.web.util;

import cl.nessfit.web.service.InstallationServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RequestValidation {

    @Autowired
    private InstallationServiceInterface installationService;

    private List<Date> listDates;
    private String days;

    public RequestValidation(String days) {
        this.listDates = new ArrayList<>();
        this.days = days;
    }

    public String getDaysMessage() throws ParseException {
        if (days.equals("")) {
            return " Debe seleccionar al menos un día";
        }

        String[] listDays = this.days.split(",");
        for (String day : listDays) {
            listDates.add(new SimpleDateFormat("dd-MM-yyyy").parse(day));
        }
        return null;
    }

    public List<Date> getListDates() {
        return this.listDates;
    }
}