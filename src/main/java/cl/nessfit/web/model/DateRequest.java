package cl.nessfit.web.model;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table (name = "date_requests")
public class DateRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -8720869883791567541L;
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    private Date date;
    @ManyToOne
    @JoinColumn (name = "id_request", nullable = false, updatable = false)
    private Request request;

    /**
     * Method that gets the id of a date request.
     * @return id of a date request.
     */
    public int getId() {
        return id;
    }
    /**
     * Method that sets the id for a date request.
     * @param id New id for the date request.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Method that gets the date of a date request.
     * @return date of a date request.
     */
    public Date getDate() {
        return date;
    }
    /**
     * Method that sets the date for a date request.
     * @param date New date for the date request.
     */
    public void setDate(Date date) {
        this.date = date;
    }
    /**
     * Method that gets the request of a date request.
     * @return request of a date request.
     */
    public Request getRequest() {
        return request;
    }
    /**
     * Method that sets the request for a date request.
     * @param request New request for the date request.
     */
    public void setRequest(Request request) {
        this.request = request;
    }
}