package com.ms.users.core;

import com.ms.users.domain.adapter.service.UserService;
import com.ms.users.domain.port.repository.UserRepositoryPort;
import com.ms.users.domain.port.service.UserServicePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanInjections {

    @Bean
    public UserServicePort userService(UserRepositoryPort userRepository){
        return new UserService(userRepository);
    }

}
