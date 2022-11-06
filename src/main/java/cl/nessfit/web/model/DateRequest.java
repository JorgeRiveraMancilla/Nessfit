package cl.nessfit.web.model;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table (name = "date_requests")
public class DateRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -8720869883791567541L;
    private LocalDate date;
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_request", referencedColumnName = "id")
    private Request request;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}