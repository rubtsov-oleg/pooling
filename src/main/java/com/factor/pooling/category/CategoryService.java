package com.factor.pooling.category;

import java.util.List;

public interface CategoryService {
    Category getCategoryById(Integer categoryId);

    List<Category> getAllCategories();

    Category saveCategory(Category category);

    void deleteCategory(Integer categoryId);

    String testConnection();
}
