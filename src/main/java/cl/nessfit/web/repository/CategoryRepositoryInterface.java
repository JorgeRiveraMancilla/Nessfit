package cl.nessfit.web.repository;

import cl.nessfit.web.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepositoryInterface extends JpaRepository<Category, Integer> {
    List<Category> findAll();
    boolean existsByName(String name);
    Category searchByName(String name);
}