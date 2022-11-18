package cl.nessfit.web.service;

import cl.nessfit.web.model.Category;
import cl.nessfit.web.repository.CategoryRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryServiceInterface {
    @Autowired
    private CategoryRepositoryInterface categoryRepository;

    /**
     * Return a list with all users with administrative role from the database.
     * @return List with all users with administrative role.
     */
    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Check if a category exists by name.
     * @param name Category name.
     * @return "True" if exists or "False" if not.
     */
    @Override
    public boolean exists(String name) {
        return categoryRepository.existsByName(name);
    }

    /**
     * Given a name, returns a category with that name data.
     * @param name Category name.
     * @return Category with that name data.
     */
    @Override
    public Category searchByName(String name) {
        return categoryRepository.searchByName(name);
    }
}