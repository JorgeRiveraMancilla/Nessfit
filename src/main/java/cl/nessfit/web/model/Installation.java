package cl.nessfit.web.model;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table (name = "installations")
public class Installation implements Serializable {
    @Serial
    private static final long serialVersionUID = 425470456250466110L;
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String address;
    @Column (name = "rental_cost")
    private String rentalCost;
    private int status;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "id_category", referencedColumnName = "id")
    private Category category;

    /**
     * Method that gets the if of an installation.
     * @return Installation id.
     */
    public int getId() { return id; }
    /**
     * Method that sets the id of an installation.
     * @param id New installation id.
     */
    public void setId(int id) { this.id = id; }
    /**
     * Method that gets the name of an installation.
     * @return Name of an installation.
     */
    public String getName() {
        return name;
    }
    /**
     * Method that sets the name of an installation.
     * @param name New name for the installation.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Method that gets the address of an installation.
     * @return Address of an installation.
     */
    public String getAddress() {
        return address;
    }
    /**
     * Method that sets the address of an installation.
     * @param address New address for the installation.
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * Method that gets the rental cost of an installation.
     * @return Rental cost of an installation.
     */
    public String getRentalCost() {
        return rentalCost;
    }
    /**
     * Method that sets the rental cost of an installation.
     * @param rentalCost New rental cost for the installation.
     */
    public void setRentalCost(String rentalCost) {
        this.rentalCost = rentalCost;
    }
    /**
     * Method that gets the status of an installation.
     * @return Status of an installation.
     */
    public int getStatus() {
        return status;
    }
    /**
     * Method that sets the status of an installation.
     * @param status New status for the installation.
     */
    public void setStatus(int status) {
        this.status = status;
    }
    /**
     * Method that gets the category of an installation.
     * @return Category of a installation.
     */
    public Category getCategory() {
        return category;
    }
    /**
     * Method that sets the category of an installation.
     * @param category New category for the installation.
     */
    public void setCategory(Category category) {
        this.category = category;
    }
}