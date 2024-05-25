package com.ms.users.domain.port.repository;

import com.ms.users.domain.model.User;

import java.util.Optional;

public interface UserRepositoryPort {


    Optional<User> findByEmail(String email);

    User save(User user);

    Optional<User> findById(Long id);

    void deleteById(Long id);

    void flush();
}
