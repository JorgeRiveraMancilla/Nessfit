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
    @Size (max = 200, message = "Largo inválido.")
    @NotEmpty (message = "Campo obligatorio.")
    private String name;
    @Size (max = 200, message = "Largo inválido.")
    @NotEmpty (message = "Campo obligatorio.")
    private String address;
    @Column (name = "rental_cost")
    @Pattern(regexp="^(\\d)+$", message = "Formato inválido.")
    private String rentalCost;
    private int status;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "id_category", referencedColumnName = "id")
    private Category category;

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

    public String getRentalCost() {
        return rentalCost;
    }

    public void setRentalCost(String rentalCost) {
        this.rentalCost = rentalCost;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}