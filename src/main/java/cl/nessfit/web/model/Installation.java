package cl.nessfit.web.model;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "installation")
public class Installation implements Serializable {

    @Serial
    private static final long serialVersionUID = -8255784159283163739L;
    @Id
    @Size(min = 1, max = 200, message = "Campo requerido.")
    private String name;
    @Size(min = 1, max = 200, message = "Campo requerido.")
    private String address;
    // Hablar con rodrigo.
    @NotBlank(message = "Campo requerido.")
    private String type;
    @Column(name = "rental_cost")
    @Min(value = 1000, message = "El costo mínimo de arriendo debe ser $1.000.")
    private int rentalCost;
    private int status;

    /**
     * Constructor for the Installation class.
     */
    public Installation() {
        this.name = "";
        this.address = "";
        this.type = "";
        this.rentalCost = 0;
        this.status = 0;
    }

    /**
     * Method that gets the name of a installation.
     * @return Name of a installation.
     */
    public String getName() {
        return name;
    }

    /**
     * Method that sets the name for a installation.
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
     * Method that sets the address for a installation.
     * @param address New address for the installation.
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * Method that gets the type of a installation.
     * @return Type of a installation.
     */
    public String getType() {
        return type;
    }
    /**
     * Method that sets the type for a installation.
     * @param type New type for the installation.
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     * Method that gets the rental cost of a installation.
     * @return Rental cost of a installation.
     */
    public int getRentalCost() {
        return rentalCost;
    }
    /**
     * Method that sets the rental cost for a installation.
     * @param rentalCost New rental cost for the installation.
     */
    public void setRentalCost(int rentalCost) {
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
     * Method that sets the status for a installation.
     * @param status New status for the installation.
     */
    public void setStatus(int status) {
        this.status = status;
    }
}