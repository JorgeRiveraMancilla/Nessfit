package cl.nessfit.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RequestValidation {

    private List<Date> listDates;
    private String days;

    /**
     * Class constructor.
     * @param days Request days.
     */
    public RequestValidation(String days) {
        this.listDates = new ArrayList<>();
        this.days = days;
    }

    /**
     * Method that get an error message from requests.
     * @return Days error message.
     * @throws ParseException Signals that an error has been reached unexpectedly while parsing.
     */
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

    /**
     * Method that get the dates from a request.
     * @return Dates from a request.
     */
    public List<Date> getListDates() {
        return this.listDates;
    }
}