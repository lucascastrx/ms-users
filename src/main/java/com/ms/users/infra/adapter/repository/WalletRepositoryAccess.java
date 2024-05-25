package com.ms.users.infra.adapter.repository;

import com.ms.users.domain.model.Wallet;
import com.ms.users.infra.adapter.model.WalletModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepositoryAccess extends JpaRepository<WalletModel, Long> {

    WalletModel findByUserId(Long userId);
}
