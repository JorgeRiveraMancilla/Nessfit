package cl.nessfit.web.service;

import cl.nessfit.web.model.Category;
import java.util.List;

public interface CategoryServiceInterface {

    /**
     * Return a list with all users with administrative role from the database.
     * @return List with all users with administrative role.
     */
    List<Category> getCategories();

    /**
     * Check if a category exists by name.
     * @param name Category name.
     * @return "True" if exists or "False" if not.
     */
    boolean exists(String name);

    /**
     * Given a name, returns a category with that name data.
     * @param name Category name.
     * @return Category with that name data.
     */
    Category searchByName(String name);
}