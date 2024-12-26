package com.factor.pooling.category;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(transactionManager = "hikariTransactionManager", readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final DataSource dataSource;
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(@Qualifier("hikariDataSource") DataSource dataSource, CategoryRepository categoryRepository) {
        this.dataSource = dataSource;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String testConnection() {
        try (Connection connection = dataSource.getConnection()) {
            String dataSourceClassName = dataSource.getClass().getName();
            return "Успешное подключение к базе данных: " + connection.getMetaData().getURL() +
                    "\nИспользуемый DataSource: " + dataSourceClassName;
        } catch (SQLException e) {
            return "Ошибка подключения к базе данных: " + e.getMessage() +
                    "\nИспользуемый DataSource: " + dataSource.getClass().getName();
        }
    }

    @Override
    public Category getCategoryById(Integer categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Категория " + categoryId + " не найдена"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }
}
