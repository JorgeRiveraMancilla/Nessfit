package cl.nessfit.web.model;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table (name = "requests")
public class Request implements Serializable {
    @Serial
    private static final long serialVersionUID = 4819729910502605019L;
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    private int status;
    private long price;
    private LocalDate register;
    private int quantity;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "rut_user", referencedColumnName = "rut")
    private User user;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "id_installation", referencedColumnName = "id")
    private Installation installation;
    @OneToMany (cascade = CascadeType.ALL, mappedBy = "request")
    private Set<DateRequest> dateRequests;
    /**
     * Method that gets the request price.
     * @return Request price.
     */
    public long getPrice() {
        return price;
    }
    /**
     * Method that set the request price.
     * @param price New installation price.
     */
    public void setPrice(long price) {
        this.price = price;
    }
    /**
     * Method that gets the id of a request.
     * @return id of a request.
     */
    public int getId() {
        return id;
    }
    /**
     * Method that sets the id for a request.
     * @param id New id for the request.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Method that gets the status of a request.
     * @return status of a request.
     */
    public int getStatus() {
        return status;
    }
    /**
     * Method that sets the status for a request.
     * @param status New status for the request.
     */
    public void setStatus(int status) {
        this.status = status;
    }
    /**
     * Method that gets the register of a request.
     * @return register of a request.
     */
    public LocalDate getRegister() {
        return register;
    }
    /**
     * Method that sets the register for a request.
     * @param register New register for the request.
     */
    public void setRegister(LocalDate register) {
        this.register = register;
    }
    /**
     * Method that gets the quantity of a request.
     * @return quantity of a request.
     */
    public int getQuantity() { return quantity; }
    /**
     * Method that sets the quantity for a request.
     * @param quantity New quantity for the request.
     */
    public void setQuantity(int quantity) { this.quantity = quantity; }
    /**
     * Method that gets the user of a request.
     * @return user of a request.
     */
    public User getUser() {
        return user;
    }
    /**
     * Method that sets the user for a request.
     * @param user New user for the request.
     */
    public void setUser(User user) {
        this.user = user;
    }
    /**
     * Method that gets the installation of a request.
     * @return installation of a request.
     */
    public Installation getInstallation() {
        return installation;
    }
    /**
     * Method that sets the installation for a request.
     * @param installation New installation for the request.
     */
    public void setInstallation(Installation installation) {
        this.installation = installation;
    }
    /**
     * Method that gets the date requests of a request.
     * @return date requests of a request.
     */
    public Set<DateRequest> getDateRequests() {
        return dateRequests;
    }
    /**
     * Method that sets the date requests for a request.
     * @param dateRequests New date requests for the request.
     */
    public void setDateRequests(Set<DateRequest> dateRequests) {
        this.dateRequests = dateRequests;
    }
}