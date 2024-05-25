package com.ms.users.domain.port.service;

import com.ms.users.domain.model.User;

public interface UserServicePort {

    User addUser(User user);

    User findById(Long id);

    void changePassword(Long id, String currentPassword, String newPassword);
}