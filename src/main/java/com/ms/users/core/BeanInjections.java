package com.ms.users.core;

import com.ms.users.domain.adapter.service.UserService;
import com.ms.users.domain.adapter.service.WalletService;
import com.ms.users.domain.port.repository.UserRepositoryPort;
import com.ms.users.domain.port.repository.WalletRepositoryPort;
import com.ms.users.domain.port.service.UserServicePort;
import com.ms.users.domain.port.service.WalletServicePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanInjections {

    @Bean
    public UserServicePort userService(UserRepositoryPort userRepository){
        return new UserService(userRepository);
    }

    @Bean
    public WalletServicePort walletService(WalletRepositoryPort walletRepository, UserServicePort userService){
        return new WalletService(walletRepository, userService);
    }
}
