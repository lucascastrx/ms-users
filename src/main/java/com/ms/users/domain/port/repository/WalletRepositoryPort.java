package com.ms.users.domain.port.repository;

import com.ms.users.domain.model.Wallet;

public interface WalletRepositoryPort {

    Wallet save(Wallet wallet);

    Wallet findByUserId(Long userId);
}
