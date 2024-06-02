package com.ms.users.UT.service;

import com.ms.users.domain.adapter.service.StakeService;
import com.ms.users.domain.adapter.service.WalletService;
import com.ms.users.domain.model.Stake;
import com.ms.users.domain.model.Wallet;
import com.ms.users.infra.adapter.repository.StakeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
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
public class StakeServiceTest {

    @Mock
    private StakeRepository stakeRepository;

    @Mock
    private WalletService walletService;

    @InjectMocks
    private StakeService stakeService;

    private Stake stake;
    private Wallet wallet;

    @BeforeEach
    public void setUp() {
        stake = new Stake();
        stake.setId(1L);
        stake.setName("Stake 1.0");
        stake.setAmount(50.0);

        wallet = new Wallet();
        wallet.setId(1L);
    }

    @Test
    public void shouldReturnStakeWhenSaveWithPositiveAmount() {
        when(walletService.findById(anyLong())).thenReturn(wallet);
        when(stakeRepository.save(any(Stake.class))).thenReturn(stake);

        Stake testStake = stakeService.addStake(stake, 1L);

        assertThat(testStake).isNotNull();
    }

    @Test
    public void shouldFailWhenSaveWithNegativeAmount() {
        stake.setAmount(-0.01);

        IllegalStateException error = assertThrows(IllegalStateException.class,
                () -> stakeService.addStake(stake, 1L));

        assertThat(error).isNotNull();
        assertEquals("The amount of the stake should be equal or higher than 0", error.getMessage());
    }

    @Test
    public void shouldReturnStakeWhenFetchingWithValidId() {
        when(stakeRepository.findById(anyLong())).thenReturn(Optional.of(stake));

        Stake testStake = stakeService.findById(1L);

        assertThat(testStake).isNotNull();
    }

    @Test
    public void shouldFailWhenFetchingWithInvalidId() {
        when(stakeRepository.findById(anyLong())).thenReturn(Optional.empty());

        EntityNotFoundException error = assertThrows(EntityNotFoundException.class,
                () -> stakeService.findById(1L));

        assertThat(error).isNotNull();
    }
}
