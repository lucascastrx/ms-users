package com.ms.users.infra.adapter.repository;

import com.ms.users.api.assembler.MapperDTO;
import com.ms.users.domain.model.User;
import com.ms.users.domain.model.Wallet;
import com.ms.users.domain.port.repository.WalletRepositoryPort;
import com.ms.users.infra.adapter.model.UserModel;
import com.ms.users.infra.adapter.model.WalletModel;
import org.springframework.stereotype.Component;

@Component
public class WalletRepository implements WalletRepositoryPort {

    private final WalletRepositoryAccess walletRepositoryAccess;
    private final MapperDTO mapperDTO;

    public WalletRepository(WalletRepositoryAccess walletRepositoryAccess, MapperDTO mapperDTO) {
        this.walletRepositoryAccess = walletRepositoryAccess;
        this.mapperDTO = mapperDTO;
    }

    @Override
    public Wallet save(Wallet wallet) {
        var walletModel = mapperDTO.transform(wallet, WalletModel.class);
        walletModel = walletRepositoryAccess.save(walletModel);
        wallet = mapperDTO.transform(walletModel, Wallet.class);
        return wallet;
    }

    @Override
    public Wallet findByUserId(Long userId) {
        var walletModel = walletRepositoryAccess.findByUserId(userId);
        var wallet = mapperDTO.transform(walletModel, Wallet.class);
        return wallet;
    }
}
