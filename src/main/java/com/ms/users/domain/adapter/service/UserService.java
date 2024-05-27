package com.ms.users.domain.adapter.service;

import com.ms.users.domain.model.User;
import com.ms.users.domain.port.repository.UserRepositoryPort;
import com.ms.users.domain.port.service.UserServicePort;
import com.ms.users.infra.producer.UserProducer;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

public class UserService implements UserServicePort {

    /**
     * Inicialmente usando exceptions padrões do java mas depois
     * podemos trabalhar em criar algumas customizadas
     */

    private final UserRepositoryPort userRepository;
    private final UserProducer userProducer;

    public UserService(final UserRepositoryPort userRepository, UserProducer userProducer) {
        this.userRepository = userRepository;
        this.userProducer = userProducer;
    }


    /**
     * Metodo utilizado para criar novo User ou atualizar um User ja existente
     * caso o email passado retorne um objeto e os ids sejam os mesmos
     *
     * @param user
     * @return user
     */
    @Override @Transactional
    public User addUser(User user){

        var userOpt = userRepository.findByEmail(user.getEmail());

        if(userOpt.isPresent() && userOpt.get().getEmail().equals(user.getEmail()))
            throw new IllegalStateException("This email is already in use");

        user = userRepository.save(user);

        userProducer.publishMessage(user);

        return user;

    }

    @Override
    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void delete (Long id){
        try {
            userRepository.deleteById(id);
            userRepository.flush();
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("Entity is not available");
        } catch (EmptyResultDataAccessException e){
            throw new RuntimeException();
        }
    }



    @Override
    public void changePassword(Long id, String currentPassword, String newPassword){
        var user = findById(id);

        if(!user.verifyPassword(currentPassword)){
            throw new DataIntegrityViolationException("Informed current password is not correct");
        }

        user.setPassword(newPassword);
        userRepository.save(user);
    }
}
