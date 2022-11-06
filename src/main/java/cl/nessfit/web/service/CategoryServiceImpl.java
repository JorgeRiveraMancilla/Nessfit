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

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public boolean exists(String name) {
        return categoryRepository.existsByName(name);
    }
}