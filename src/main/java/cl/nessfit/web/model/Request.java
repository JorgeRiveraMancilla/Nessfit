package cl.nessfit.web.model;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table (name = "requests")
public class Request implements Serializable {
    @Serial
    private static final long serialVersionUID = 4819729910502605019L;
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private int id;
    private int status;
    private Date register;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "rut_user", referencedColumnName = "rut")
    private User user;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "name_installation", referencedColumnName = "name")
    private Installation installation;
    @OneToMany (cascade = CascadeType.ALL, mappedBy = "request")
    private Set<DateRequest> dateRequests;

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

    public Date getRegister() {
        return register;
    }

    public void setRegister(Date register) {
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

    public Set<DateRequest> getDateRequests() {
        return dateRequests;
    }

    public void setDateRequests(Set<DateRequest> dateRequests) {
        this.dateRequests = dateRequests;
    }
}