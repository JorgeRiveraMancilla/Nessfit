package cl.nessfit.web.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table (name = "installations")
public class Installation implements Serializable {
    @Serial
    private static final long serialVersionUID = 425470456250466110L;
    @Id
    @NotBlank (message = "Campo obligatorio.")
    @Size (max = 200, message = "Largo inválido.")
    private String name;
    @NotBlank (message = "Campo obligatorio.")
    @Size (max = 200, message = "Largo inválido.")
    private String address;
    @Column (name = "rental_cost")
    @NotBlank (message = "Campo obligatorio.")
    @Pattern(regexp="^(\\d)+$", message = "El costo deben contener solo números.")
    private String rentalCost;
    private int status;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "id_category", referencedColumnName = "id")
    private Category category;

    /**
     * Constructor for the User class.
     */
    public Installation() {
        this.name = "";
        this.address = "";
        this.rentalCost = "";
        this.status = 0;
        this.category = null;
    }
    /**
     * Method that gets the name of a installation.
     * @return Name of a installation.
     */
    public String getName() {
        return name;
    }
    /**
     * Method that sets the name of a installation.
     * @param name New name for the installation.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Method that gets the address of a installation.
     * @return Address of a installation.
     */
    public String getAddress() {
        return address;
    }
    /**
     * Method that sets the address of a installation.
     * @param address New address for the installation.
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * Method that gets the rental cost of a installation.
     * @return Rental cost of a installation.
     */
    public String getRentalCost() {
        return rentalCost;
    }
    /**
     * Method that sets the rental cost of a installation.
     * @param rentalCost New rental cost for the installation.
     */
    public void setRentalCost(String rentalCost) {
        this.rentalCost = rentalCost;
    }
    /**
     * Method that gets the status of a installation.
     * @return Status of a installation.
     */
    public int getStatus() {
        return status;
    }
    /**
     * Method that sets the status of a installation.
     * @param status New status for the installation.
     */
    public void setStatus(int status) {
        this.status = status;
    }
    /**
     * Method that gets the category of a installation.
     * @return Category of a installation.
     */
    public Category getCategory() {
        return category;
    }
    /**
     * Method that sets the category of a installation.
     * @param name New category for the installation.
     */
    public void setCategory(Category category) {
        this.category = category;
    }
}