package com.ms.users.UT.service;

import com.ms.users.domain.adapter.service.UserService;
import com.ms.users.domain.adapter.service.WalletService;
import com.ms.users.domain.model.User;
import com.ms.users.domain.model.Wallet;
import com.ms.users.infra.adapter.repository.WalletRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private WalletService walletService;

    @Test
    public void shouldReturnWalletWhenSavingWithPositiveBalance(){
        var user = new User();
        user.setId(1L);
        user.setName("Lucas");
        user.setUsername("Lucas");
        user.setEmail("lucas@gmail.com");
        user.setPassword("lucas");

        var wallet = new Wallet();
        wallet.setBalance(150.00);

        var savedWallet = new Wallet();
        savedWallet.setId(1L);
        savedWallet.setBalance(150.00);
        savedWallet.setUser(user);

        when(userService.findById(anyLong())).thenReturn(user);
        when(walletRepository.save(any(Wallet.class))).thenReturn(savedWallet);

        var testWallet = walletService.addWallet(wallet, 1L);

        assertThat(testWallet).isNotNull();
    }

    @Test
    public void shouldFailWhenSavingWalletWithNegativeBalance(){
        var wallet = new Wallet();
        wallet.setBalance(-150.00);

        IllegalStateException error = assertThrows(IllegalStateException.class,
                ()-> walletService.addWallet(wallet, 1L));

        assertThat(error).isNotNull();
    }

    @Test
    public void shouldReturnWalletWhenFetchingWithValidId(){
        var user = new User();
        user.setId(1L);
        user.setName("Lucas");
        user.setUsername("Lucas");
        user.setEmail("lucas@gmail.com");
        user.setPassword("lucas");

        var wallet = new Wallet();
        wallet.setId(1L);
        wallet.setBalance(150.00);
        wallet.setUser(user);

        when(walletRepository.findById(anyLong())).thenReturn(Optional.of(wallet));

        var testWallet = walletService.findById(1L);

        assertThat(testWallet).isNotNull();
    }

    @Test
    public void shouldFailWhenFetchingWithInvalidId(){
        when(walletRepository.findById(anyLong())).thenReturn(Optional.empty());

        EntityNotFoundException error = assertThrows(EntityNotFoundException.class,
                () -> walletService.findById(1L));

        assertThat(error).isNotNull();
    }

    @Test
    public void shouldReturnWalletWhenDepositingWithPositiveAmount(){
        var user = new User();
        user.setId(1L);
        user.setName("Lucas");
        user.setUsername("Lucas");
        user.setEmail("lucas@gmail.com");
        user.setPassword("lucas");

        var wallet = new Wallet();
        wallet.setId(1L);
        wallet.setBalance(150.00);
        wallet.setUser(user);

        var testWallet = walletService.deposit(wallet, 20.00);

        assertThat(testWallet.getBalance()).isEqualTo(170.00);
    }

    @Test
    public void shoulFailWhenDepositWithNegativeAmount(){
        var wallet = new Wallet();

        IllegalStateException error = assertThrows(IllegalStateException.class,
                () -> walletService.deposit(wallet, -0.01));

        assertThat(error).isNotNull();
        assertEquals("The amount deposited should be positive", error.getMessage());
    }

    @Test
    public void shouldReturnWalletWhenWithdrawingWithPositiveAmount(){
        var user = new User();
        user.setId(1L);
        user.setName("Lucas");
        user.setUsername("Lucas");
        user.setEmail("lucas@gmail.com");
        user.setPassword("lucas");

        var wallet = new Wallet();
        wallet.setId(1L);
        wallet.setBalance(150.00);
        wallet.setUser(user);

        var testWallet = walletService.withdraw(wallet, 20.00);

        assertThat(testWallet.getBalance()).isEqualTo(130.00);
    }

    @Test
    public void shoulFailWhenWithdrawingWithNegativeAmount(){
        var wallet = new Wallet();

        IllegalStateException error = assertThrows(IllegalStateException.class,
                () -> walletService.withdraw(wallet, -0.01));

        assertThat(error).isNotNull();
        assertEquals("The amount withdrawn should be positive", error.getMessage());
    }
}
