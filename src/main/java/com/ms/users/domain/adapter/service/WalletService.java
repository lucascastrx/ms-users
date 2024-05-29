package com.ms.users.domain.adapter.service;

import com.ms.users.domain.model.Wallet;
import com.ms.users.domain.port.repository.WalletRepositoryPort;
import com.ms.users.domain.port.service.UserServicePort;
import com.ms.users.domain.port.service.WalletServicePort;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public class WalletService implements WalletServicePort {

    private final WalletRepositoryPort walletRepository;
    private final UserServicePort userService;

    public WalletService(WalletRepositoryPort walletRepository, UserServicePort userService) {
        this.walletRepository = walletRepository;
        this.userService = userService;
    }

    @Override
//    @Transactional
    public Wallet addWallet(Wallet wallet, Long userId) {

        if (wallet.getBalance() < 0)
            throw new IllegalStateException("The balance value should be equal or higher than 0");

        var user = userService.findById(userId);
        wallet.setUser(user);

        return walletRepository.save(wallet);
    }

    @Override
    public Wallet retrieveWalletByUser(Long userId) {
        return walletRepository.findByUserId(userId);
    }

    @Override
    public Wallet findById(Long walletId){
        return walletRepository.findById(walletId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Wallet deposit(Wallet wallet, double amount) {
        if(amount < 0)
            throw new IllegalStateException("The amount deposited should be positive");

        wallet.setBalance(wallet.getBalance() + amount);
        return wallet;
    }

    @Override
    public Wallet withdraw(Wallet wallet, double amount) {
        if(amount > 0)
            throw new IllegalStateException("The amount withdrawn should be positive");

        wallet.setBalance(wallet.getBalance() - amount);
        return wallet;
    }
}
