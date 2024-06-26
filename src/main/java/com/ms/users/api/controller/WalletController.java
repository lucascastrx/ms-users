package com.ms.users.api.controller;

import com.ms.users.api.assembler.MapperDTO;
import com.ms.users.api.dto.wallet.input.WalletBalanceDTO;
import com.ms.users.api.dto.wallet.output.WalletDTO;
import com.ms.users.domain.model.Wallet;
import com.ms.users.domain.port.service.WalletServicePort;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/wallet")
public class WalletController {

    private final WalletServicePort walletService;
    private final MapperDTO mapper;

    public WalletController(WalletServicePort walletService, MapperDTO mapper) {
        this.walletService = walletService;
        this.mapper = mapper;
    }

    @GetMapping
    public WalletDTO retrieveWallet(@PathVariable Long userId){
        return mapper.transform(walletService.retrieveWalletByUser(userId), WalletDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WalletDTO addWallet(@PathVariable Long userId, @RequestBody @Valid WalletBalanceDTO walletBalanceDTO){
        var wallet = mapper.transform(walletBalanceDTO, Wallet.class);
        wallet = walletService.addWallet(wallet, userId);
        return mapper.transform(wallet, WalletDTO.class);
    }

    @PutMapping("/deposit")
    public WalletDTO depositOnWallet(@PathVariable Long userId, @RequestBody @Valid WalletBalanceDTO walletBalanceDTO){
        var wallet = walletService.retrieveWalletByUser(userId);
        wallet = walletService.deposit(wallet, walletBalanceDTO.balance());
        wallet = walletService.addWallet(wallet, userId);
        return mapper.transform(wallet, WalletDTO.class);
    }

    @PutMapping("/withdraw")
    public WalletDTO withdrawFromWallet(@PathVariable Long userId, @RequestBody @Valid WalletBalanceDTO walletBalanceDTO){
        var wallet = walletService.retrieveWalletByUser(userId);
        wallet = walletService.withdraw(wallet, walletBalanceDTO.balance());
        wallet = walletService.addWallet(wallet, userId);
        return mapper.transform(wallet, WalletDTO.class);
    }
}
