package com.ms.users.domain.port.service;

import com.ms.users.domain.model.Wallet;

public interface WalletServicePort {

    Wallet addWallet(Wallet wallet, Long userId);

    Wallet retrieveWalletByUser(Long userId);

    Wallet findById(Long walletId);

    Wallet deposit(Wallet wallet, double amount);

    Wallet withdraw(Wallet wallet, double amount);
}
