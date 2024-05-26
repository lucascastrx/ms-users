package com.ms.users;


import com.ms.users.domain.model.Stake;
import com.ms.users.domain.model.User;
import com.ms.users.domain.model.Wallet;
import com.ms.users.domain.port.service.StakeServicePort;
import com.ms.users.domain.port.service.UserServicePort;
import com.ms.users.domain.port.service.WalletServicePort;
import com.ms.users.infra.adapter.repository.StakeRepositoryAccess;
import com.ms.users.infra.adapter.repository.UserRepositoryAccess;
import com.ms.users.infra.adapter.repository.WalletRepositoryAccess;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class StakeTest {

    @Autowired
    private StakeRepositoryAccess databaseAccess;
    @Autowired
    private StakeServicePort stakeService;
    @Autowired
    private UserRepositoryAccess userAccess;
    @Autowired
    private UserServicePort userService;
    @Autowired
    private WalletRepositoryAccess walletAccess;
    @Autowired
    private WalletServicePort walletService;

    private User user;
    private Wallet wallet;

    @BeforeEach
    public void setUp(){

        this.user = new User();
        this.user.setName("Lucas");
        this.user.setUsername("lvcvx");
        this.user.setEmail("luukascastro@gmail.com");
        this.user.setPassword("lucas");
        this.user = userService.addUser(user);

        this.wallet = new Wallet();
        this.wallet.setBalance(150.00);
        this.wallet = walletService.addWallet(wallet, this.user.getId());
    }

    @AfterEach
    public void cleanUp(){
        if(user != null){
            walletAccess.deleteById(this.wallet.getId());
            userAccess.deleteById(this.user.getId());
        }
    }

    @Test
    public void shouldSucceedWhenCreateStakeWithAllData(){
        var stake = new Stake();
        stake.setName("Stake 1un");
        stake.setAmount(50.00);

        stake = stakeService.addStake(stake, this.wallet.getId());

        assertThat(stake).isNotNull();
        assertThat(stake.getId()).isGreaterThan(0);
    }

    @Test
    public void shoulFailWhenFetchingNonexistingStake(){
        EntityNotFoundException error = assertThrows(EntityNotFoundException.class,
                ()-> stakeService.findById(1L));
        assertThat(error).isNotNull();
    }
}
