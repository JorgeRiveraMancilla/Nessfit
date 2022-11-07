package cl.nessfit.web.service;

import cl.nessfit.web.model.Category;
import java.util.List;

public interface CategoryServiceInterface {
    List<Category> getCategories();
    boolean exists(String name);
    Category searchByName(String name);
}