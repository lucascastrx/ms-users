package com.ms.users.infra.adapter.repository;

import com.ms.users.infra.adapter.model.WalletModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepositoryAccess extends JpaRepository<WalletModel, Long> {
}
