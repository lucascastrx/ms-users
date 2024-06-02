package com.ms.users.UT.repository;

import com.ms.users.api.assembler.MapperDTO;
import com.ms.users.domain.model.Stake;
import com.ms.users.domain.model.User;
import com.ms.users.domain.model.Wallet;
import com.ms.users.infra.adapter.model.StakeModel;
import com.ms.users.infra.adapter.model.UserModel;
import com.ms.users.infra.adapter.model.WalletModel;
import com.ms.users.infra.adapter.repository.StakeRepository;
import com.ms.users.infra.adapter.repository.StakeRepositoryAccess;
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
public class StakeRepositoryTest {

    @Mock
    private StakeRepositoryAccess stakeRepositoryAccess;

    @Mock
    private MapperDTO mapper;

    @InjectMocks
    private StakeRepository stakeRepository;

    @Test
    public void shouldReturnStakeWhenSavingWithMapper(){
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

        var stake = new Stake();
        stake.setName("Stake 1.5");
        stake.setAmount(25.00);
        stake.setWallet(wallet);

        var userModel = new UserModel();
        userModel.setId(1L);
        userModel.setName("Lucas");
        userModel.setUsername("Lucas");
        userModel.setEmail("lucas@gmail.com");
        userModel.setPassword("lucas");

        var walletModel = new WalletModel();
        walletModel.setId(1L);
        walletModel.setBalance(150.00);
        walletModel.setUser(userModel);

        var stakeModel = new StakeModel();
        stakeModel.setName("Stake 1.5");
        stakeModel.setAmount(25.00);
        stakeModel.setWallet(walletModel);

        var savedStakeModel = new StakeModel();
        savedStakeModel.setId(1L);
        savedStakeModel.setName("Stake 1.5");
        savedStakeModel.setAmount(25.00);
        savedStakeModel.setWallet(walletModel);

        var savedStake = new Stake();
        savedStake.setId(1L);
        savedStake.setName("Stake 1.5");
        savedStake.setAmount(25.00);
        savedStake.setWallet(wallet);

        when(mapper.transform(any(Stake.class), same(StakeModel.class))).thenReturn(stakeModel);
        when(stakeRepositoryAccess.save(any(StakeModel.class))).thenReturn(savedStakeModel);
        when(mapper.transform(any(StakeModel.class), same(Stake.class))).thenReturn(savedStake);

        var testStake = stakeRepository.save(stake);

        assertThat(testStake).isNotNull();
        assertThat(testStake.getId()).isGreaterThan(0);
    }

    @Test
    public void shouldReturnStakeWhenFetchWithId(){
        var userModel = new UserModel();
        userModel.setId(1L);
        userModel.setName("Lucas");
        userModel.setUsername("Lucas");
        userModel.setEmail("lucas@gmail.com");
        userModel.setPassword("lucas");

        var walletModel = new WalletModel();
        walletModel.setId(1L);
        walletModel.setBalance(150.00);
        walletModel.setUser(userModel);

        var stakeModel = new StakeModel();
        stakeModel.setId(1L);
        stakeModel.setName("Stake 1.5");
        stakeModel.setAmount(25.00);
        stakeModel.setWallet(walletModel);

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

        var stake = new Stake();
        stake.setId(1L);
        stake.setName("Stake 1.5");
        stake.setAmount(25.00);
        stake.setWallet(wallet);

        when(stakeRepositoryAccess.findById(anyLong())).thenReturn(Optional.of(stakeModel));
        when(mapper.transform(any(StakeModel.class), same(Stake.class))).thenReturn(stake);


        var testWallet = stakeRepository.findById(1L).get();
        assertThat(testWallet).isNotNull();
        assertThat(testWallet.getId()).isGreaterThan(0);
    }


}
