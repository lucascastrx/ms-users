package com.ms.users.core;

import org.modelmapper.ModelMapper;
import org.modelmapper.record.RecordModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        var mapper = new ModelMapper();
        mapper.registerModule(new RecordModule());
        return mapper;
    }
}
