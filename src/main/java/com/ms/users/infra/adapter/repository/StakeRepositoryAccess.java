package com.ms.users.infra.adapter.repository;

import com.ms.users.infra.adapter.model.StakeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StakeRepositoryAccess extends JpaRepository<StakeModel, Long> {

    List<StakeModel> findAllByWalletId(Long id);
}
