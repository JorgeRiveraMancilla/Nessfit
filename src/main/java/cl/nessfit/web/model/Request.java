package cl.nessfit.web.model;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "requests")
public class Request implements Serializable {
    @Serial
    private static final long serialVersionUID = 4819729910502605019L;
    @Id
    private int id;
    private int status;
    private LocalDate register;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "rut_user", referencedColumnName = "rut")
    private User user;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "name_installation", referencedColumnName = "name")
    private Installation installation;
    @OneToMany
    @JoinColumn (name = "id_date_request", referencedColumnName = "id")
    private List<DateRequest> dateRequests = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDate getRegister() {
        return register;
    }

    public void setRegister(LocalDate register) {
        this.register = register;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Installation getInstallation() {
        return installation;
    }

    public void setInstallation(Installation installation) {
        this.installation = installation;
    }

    public List<DateRequest> getDateRequests() {
        return dateRequests;
    }

    public void setDateRequests(List<DateRequest> dateRequests) {
        this.dateRequests = dateRequests;
    }
}