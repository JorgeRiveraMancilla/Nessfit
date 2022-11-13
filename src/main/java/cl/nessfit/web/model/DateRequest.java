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
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private int id;
    private Date date;
    @ManyToOne
    @JoinColumn (name = "id_request", nullable = false, updatable = false)
    private Request request;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}