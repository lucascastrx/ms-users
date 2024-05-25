package com.ms.users.infra.adapter.repository;

import com.ms.users.api.assembler.MapperDTO;
import com.ms.users.domain.model.User;
import com.ms.users.domain.port.repository.UserRepositoryPort;
import com.ms.users.infra.adapter.model.UserModel;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepository implements UserRepositoryPort {

    private final UserRepositoryAccess userRepositoryAccess;
    private final MapperDTO mapperDTO;

    public UserRepository(UserRepositoryAccess userRepositoryAccess, MapperDTO mapperDTO) {
        this.userRepositoryAccess = userRepositoryAccess;
        this.mapperDTO = mapperDTO;
    }

    //Colocar mesmos metodos que eram acessados pela interface que estende de jparepository aqui e puxar diretamente por aqui

    @Override
    public Optional<User> findByEmail(String email){
        var userModel = userRepositoryAccess.findByEmail(email);
        if(userModel.isEmpty()) return Optional.empty();

        var user = mapperDTO.transform(userModel.get(), User.class);
        return Optional.of(user);
    }

    @Override
    public User save(User user){
        var userModel = mapperDTO.transform(user, UserModel.class);
        userModel = userRepositoryAccess.save(userModel);
        user = mapperDTO.transform(userModel, User.class);
        return user;
    }

    @Override
    public Optional<User> findById(Long id){
        var userModel = userRepositoryAccess.findById(id);
        var user = mapperDTO.transform(userModel, User.class);
        return Optional.of(user);
    }

    @Override
    public void deleteById(Long id){
        userRepositoryAccess.deleteById(id);
    }

    @Override
    public void flush(){
        userRepositoryAccess.flush();
    }
}
