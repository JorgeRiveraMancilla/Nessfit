package cl.nessfit.web.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "categories")
public class Category implements Serializable {
    @Serial
    private static final long serialVersionUID = 3719789859065006975L;
    @Id
    private int id;
    private String name;

    /**
     * Method that gets the id of a category.
     * @return id of a category.
     */
    public int getId() {
        return id;
    }
    /**
     * Method that sets the id for a category.
     * @param id New id for the category.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Method that gets the name of a category.
     * @return name of a category.
     */
    public String getName() {
        return name;
    }
    /**
     * Method that sets the name for a category.
     * @param name New name for the category.
     */
    public void setName(String name) {
        this.name = name;
    }
}