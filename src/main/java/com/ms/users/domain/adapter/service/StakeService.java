package com.ms.users.domain.adapter.service;

import com.ms.users.domain.model.Stake;
import com.ms.users.domain.port.repository.StakeRepositoryPort;
import com.ms.users.domain.port.service.StakeServicePort;
import com.ms.users.domain.port.service.WalletServicePort;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

public class StakeService implements StakeServicePort {

    private final StakeRepositoryPort stakeRepository;
    private final WalletServicePort walletService;

    public StakeService(StakeRepositoryPort stakeRepository, WalletServicePort walletService) {
        this.stakeRepository = stakeRepository;
        this.walletService = walletService;
    }

    @Override
    public Stake addStake(Stake stake, Long walletId) {
        if (stake.getAmount() < 0)
            throw new IllegalStateException("The amount of the stake should be equal or higher than 0");

        var wallet = walletService.findById(walletId);
        stake.setWallet(wallet);
        return stakeRepository.save(stake);
    }

    @Override
    @Transactional
    public void delete(Long stakeId) {
        try {
            stakeRepository.deleteById(stakeId);
            stakeRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Entity is not available");
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Stake findById(Long id){
        return stakeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

}
