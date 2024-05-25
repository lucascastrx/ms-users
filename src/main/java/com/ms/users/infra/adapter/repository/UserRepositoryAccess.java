package com.ms.users.infra.adapter.repository;

import com.ms.users.domain.model.User;
import com.ms.users.infra.adapter.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositoryAccess extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findByEmail(String email);
}