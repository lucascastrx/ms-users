package com.ms.users.domain.port.repository;

import com.ms.users.domain.model.Stake;

import java.util.Optional;

public interface StakeRepositoryPort {
    Stake save(Stake stake);

    Optional<Stake> findById(Long id);

    void deleteById(Long id);

    void flush();
}
