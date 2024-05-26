package com.ms.users.infra.adapter.repository;

import com.ms.users.api.assembler.MapperDTO;
import com.ms.users.domain.model.Wallet;
import com.ms.users.domain.port.repository.WalletRepositoryPort;
import com.ms.users.infra.adapter.model.WalletModel;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class WalletRepository implements WalletRepositoryPort {

    private final WalletRepositoryAccess walletRepositoryAccess;
    private final MapperDTO mapper;

    public WalletRepository(WalletRepositoryAccess walletRepositoryAccess, MapperDTO mapper) {
        this.walletRepositoryAccess = walletRepositoryAccess;
        this.mapper = mapper;
    }

    @Override
    public Wallet save(Wallet wallet) {
        var walletModel = mapper.transform(wallet, WalletModel.class);
        walletModel = walletRepositoryAccess.save(walletModel);
        wallet = mapper.transform(walletModel, Wallet.class);
        return wallet;
    }

    @Override
    public Wallet findByUserId(Long userId) {
        var walletModel = walletRepositoryAccess.findByUserId(userId);
        var wallet = mapper.transform(walletModel, Wallet.class);
        return wallet;
    }

    @Override
    public Optional<Wallet> findById(Long id){
        var walletModel = walletRepositoryAccess.findById(id);
        var wallet = mapper.transform(walletModel, Wallet.class);
        return Optional.of(wallet);
    }
}
