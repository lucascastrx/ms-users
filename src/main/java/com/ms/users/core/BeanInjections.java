package com.ms.users.core;

import com.ms.users.domain.adapter.service.StakeService;
import com.ms.users.domain.adapter.service.UserService;
import com.ms.users.domain.adapter.service.WalletService;
import com.ms.users.domain.port.repository.StakeRepositoryPort;
import com.ms.users.domain.port.repository.UserRepositoryPort;
import com.ms.users.domain.port.repository.WalletRepositoryPort;
import com.ms.users.domain.port.service.StakeServicePort;
import com.ms.users.domain.port.service.UserServicePort;
import com.ms.users.domain.port.service.WalletServicePort;
import com.ms.users.infra.producer.UserProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanInjections {

    @Bean
    public UserServicePort userService(UserRepositoryPort userRepository, UserProducer userProducer){
        return new UserService(userRepository, userProducer);
    }

    @Bean
    public WalletServicePort walletService(WalletRepositoryPort walletRepository, UserServicePort userService){
        return new WalletService(walletRepository, userService);
    }

    @Bean
    public StakeServicePort stakeService(StakeRepositoryPort stakeRepository, WalletServicePort walletService){
        return new StakeService(stakeRepository, walletService);
    }
}
