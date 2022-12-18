package cl.nessfit.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RequestValidation {

    private List<LocalDate> listDates;
    private String days;

    /**
     * Class constructor.
     * @param days Request days.
     */
    public RequestValidation(String days) {
        this.listDates = new ArrayList<LocalDate>();
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
            System.out.println(day);
            listDates.add(LocalDate.parse(day, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
        return null;
    }

    /**
     * Method that get the dates from a request.
     * @return Dates from a request.
     */
    public List<LocalDate> getListDates() {
        return this.listDates;
    }
}