package com.ms.users.UT.repository;

import com.ms.users.api.assembler.MapperDTO;
import com.ms.users.domain.model.User;
import com.ms.users.domain.model.Wallet;
import com.ms.users.infra.adapter.model.UserModel;
import com.ms.users.infra.adapter.model.WalletModel;
import com.ms.users.infra.adapter.repository.WalletRepository;
import com.ms.users.infra.adapter.repository.WalletRepositoryAccess;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WalletRepositoryTest {

    @Mock
    private WalletRepositoryAccess walletRepositoryAccess;

    @Mock
    private MapperDTO mapper;

    @InjectMocks
    private WalletRepository walletRepository;

    @Test
    public void shouldReturnWalletWhenSavingWalletWithMapper(){
        var user = new User();
        user.setId(1L);
        user.setName("Lucas");
        user.setUsername("Lucas");
        user.setEmail("lucas@gmail.com");
        user.setPassword("lucas");

        var userModel = new UserModel();
        userModel.setId(1L);
        userModel.setName("Lucas");
        userModel.setUsername("Lucas");
        userModel.setEmail("lucas@gmail.com");
        userModel.setPassword("lucas");

        var walletModel = new WalletModel();
        walletModel.setBalance(150.00);
        walletModel.setUser(userModel);

        var savedWalletModel = new WalletModel();
        savedWalletModel.setId(1L);
        savedWalletModel.setBalance(150.00);
        savedWalletModel.setUser(userModel);

        var savedWallet = new Wallet();
        savedWallet.setId(1L);
        savedWallet.setBalance(150.00);
        savedWallet.setUser(user);

        var wallet = new Wallet();
        wallet.setBalance(150.00);
        wallet.setUser(user);

        when(mapper.transform(any(Wallet.class), same(WalletModel.class))).thenReturn(walletModel);
        when(walletRepositoryAccess.save(any(WalletModel.class))).thenReturn(savedWalletModel);
        when(mapper.transform(any(WalletModel.class), same(Wallet.class))).thenReturn(savedWallet);

        var testWallet = walletRepository.save(wallet);

        assertThat(testWallet).isNotNull();
        assertThat(testWallet.getId()).isGreaterThan(0);


    }

    @Test
    public void shouldReturnWalletWhenFetchWithId(){
        var userModel = new UserModel();
        userModel.setId(1L);
        userModel.setName("Lucas");
        userModel.setUsername("Lucas");
        userModel.setEmail("lucas@gmail.com");
        userModel.setPassword("lucas");

        var walletModel = new WalletModel();
        walletModel.setBalance(150.00);
        walletModel.setUser(userModel);

        var user = new User();
        user.setId(1L);
        user.setName("Lucas");
        user.setUsername("Lucas");
        user.setEmail("lucas@gmail.com");
        user.setPassword("lucas");

        var savedWallet = new Wallet();
        savedWallet.setId(1L);
        savedWallet.setBalance(150.00);
        savedWallet.setUser(user);


        when(walletRepositoryAccess.findById(anyLong())).thenReturn(Optional.of(walletModel));
        when(mapper.transform(any(Optional.class), same(Wallet.class))).thenReturn(savedWallet);


        var testWallet = walletRepository.findById(1L).get();
        assertThat(testWallet).isNotNull();
        assertThat(testWallet.getId()).isGreaterThan(0);
        assertThat(testWallet).usingRecursiveComparison().isEqualTo(savedWallet);
    }

    @Test
    public void shouldReturnWalletWhenFetchWithUserId(){
        var userModel = new UserModel();
        userModel.setId(1L);
        userModel.setName("Lucas");
        userModel.setUsername("Lucas");
        userModel.setEmail("lucas@gmail.com");
        userModel.setPassword("lucas");

        var walletModel = new WalletModel();
        walletModel.setBalance(150.00);
        walletModel.setUser(userModel);

        var user = new User();
        user.setId(1L);
        user.setName("Lucas");
        user.setUsername("Lucas");
        user.setEmail("lucas@gmail.com");
        user.setPassword("lucas");

        var savedWallet = new Wallet();
        savedWallet.setId(1L);
        savedWallet.setBalance(150.00);
        savedWallet.setUser(user);


        when(walletRepositoryAccess.findByUserId(anyLong())).thenReturn(walletModel);
        when(mapper.transform(any(WalletModel.class), same(Wallet.class))).thenReturn(savedWallet);


        var testWallet = walletRepository.findByUserId(1L);
        assertThat(testWallet).isNotNull();
        assertThat(testWallet.getId()).isGreaterThan(0);
        assertThat(testWallet).usingRecursiveComparison().isEqualTo(savedWallet);
    }
}
