package cl.nessfit.web.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table (name = "installations")
public class Installation implements Serializable {
    @Serial
    private static final long serialVersionUID = 425470456250466110L;
    @Id
    @Size (min = 1, max = 200, message = "Largo inválido.")
    private String name;
    @Size (min = 1, max = 200, message = "Largo inválido.")
    private String address;
    @Column (name = "rental_cost")
    @Min (value = 1000, message = "El costo mínimo de arriendo debe ser $1000.")
    private int rentalCost;
    private int status;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "id_type", referencedColumnName = "id")
    private Type type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRentalCost() {
        return rentalCost;
    }

    public void setRentalCost(int rentalCost) {
        this.rentalCost = rentalCost;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}