package cl.nessfit.web.util;

import cl.nessfit.web.service.CategoryServiceInterface;

public class CategoryValidation {
    public static boolean exists(CategoryServiceInterface categoryService, String category) {
        return categoryService.exists(category);
    }
}