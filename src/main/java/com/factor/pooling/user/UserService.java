package com.factor.pooling.user;

import java.util.List;

public interface UserService {
    User getUserById(Integer userId);

    List<User> getAllUsers();

    User saveUser(User user);

    void deleteUser(Integer userId);

    String testConnection();
}