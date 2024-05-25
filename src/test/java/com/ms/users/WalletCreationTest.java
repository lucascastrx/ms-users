package com.ms.users;


import com.ms.users.domain.model.User;
import com.ms.users.domain.model.Wallet;
import com.ms.users.domain.port.service.UserServicePort;
import com.ms.users.domain.port.service.WalletServicePort;
import com.ms.users.infra.adapter.repository.UserRepositoryAccess;
import com.ms.users.infra.adapter.repository.WalletRepositoryAccess;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class WalletCreationTest {

    @Autowired
    private WalletServicePort walletService;

    @Autowired
    private UserServicePort userService;

    private static User user;

    @Autowired
    private WalletRepositoryAccess databaseAccess;

    @Autowired
    private UserRepositoryAccess userAccess;

    @BeforeAll
    public static void initialSetUp(){

    }

    @BeforeEach
    public void setUp(){
        databaseAccess.deleteAllInBatch();
        userAccess.deleteAllInBatch();

        this.user = new User();
        this.user.setName("Lucas");
        this.user.setUsername("lvcvx");
        this.user.setEmail("luukascastro@gmail.com");
        this.user.setPassword("lucas");
        this.user = userService.addUser(user);
    }

    @Test
    public void shouldSucceedWhenWalletCreatedWithAllData(){

        var wallet = new Wallet();
        wallet.setBalance(500.00);

        wallet = walletService.addWallet(wallet, this.user.getId());

        assertThat(wallet).isNotNull();
        assertThat(wallet.getId()).isGreaterThan(0);
    }

    @Test
    public void shoulFailWhenWalletBalanceIsNegative(){
        var wallet = new Wallet();
        wallet.setBalance(-500.00);

        IllegalStateException error = assertThrows(IllegalStateException.class,
                ()-> walletService.addWallet(wallet, this.user.getId()));

        assertThat(error).isNotNull();
    }

    @Test
    public void shouldSucceedWhenFetchingWalletWithUserId(){
        var wallet = new Wallet();
        wallet.setBalance(500.00);

        wallet = walletService.addWallet(wallet, this.user.getId());

        var secondWallet = walletService.retrieveWallet(this.user.getId());

        assertThat(secondWallet).usingRecursiveComparison().isEqualTo(wallet);
    }
}
