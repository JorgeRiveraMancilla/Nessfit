package cl.nessfit.web.repository;

import cl.nessfit.web.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepositoryInterface extends JpaRepository<Category, Integer> {

    /**
     * Select *;
     * @return All categories from the database.
     */
    List<Category> findAll();

    /**
     * Checks whether the data store contains categories that match the name given.
     * @param name Category name.
     * @return "True" if exist or "False" if not.
     */
    boolean existsByName(String name);

    /**
     * Select * from categories c where c.name = name.
     * @param name name from category.
     * @return Category.
     */
    Category searchByName(String name);
}