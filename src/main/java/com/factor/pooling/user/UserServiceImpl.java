package com.factor.pooling.user;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(transactionManager = "dbcp2TransactionManager", readOnly = true)
public class UserServiceImpl implements UserService {

    private final DataSource dataSource;
    private final UserRepository userRepository;

    public UserServiceImpl(@Qualifier("dbcp2DataSource") DataSource dataSource, UserRepository userRepository) {
        this.dataSource = dataSource;
        this.userRepository = userRepository;
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
    public User getUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Пользователь " + userId + " не найден"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
