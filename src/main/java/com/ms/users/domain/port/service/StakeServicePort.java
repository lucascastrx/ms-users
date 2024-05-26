package com.ms.users.domain.port.service;

import com.ms.users.domain.model.Stake;

import java.util.List;

public interface StakeServicePort {

    Stake addStake(Stake stake, Long walletId);

    void delete(Long stakeId);

    Stake findById(Long id);

}
