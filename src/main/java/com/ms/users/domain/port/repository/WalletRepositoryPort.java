package com.ms.users.domain.port.repository;

import com.ms.users.domain.model.Wallet;

import java.util.Optional;

public interface WalletRepositoryPort {

    Wallet save(Wallet wallet);

    Wallet findByUserId(Long userId);

    Optional<Wallet> findById(Long id);
}
