package com.ms.users.domain.adapter.service;

import com.ms.users.domain.model.User;
import com.ms.users.domain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    /**
     * Inicialmente usando exceptions padrões do java mas depois
     * podemos trabalhar em criar algumas customizadas
     */

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * Metodo utilizado para criar novo User ou atualizar um User ja existente
     * caso o email passado retorne um objeto e os ids sejam os mesmos
     *
     * @param user
     * @return user
     */
    public User addUser(User user){

        var userOpt = userRepository.findByEmail(user.getEmail());

        if(userOpt.isPresent() && userOpt.get().equals(user))
            throw new IllegalStateException("This email is already in use");

        return userRepository.save(user);
    }

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

    public void changePassword(Long id, String currentPassword, String newPassword){
        var user = findById(id);

        if(!user.verifyPassword(currentPassword)){
            throw new DataIntegrityViolationException("Informed current password is not correct");
        }

        user.setPassword(newPassword);
    }
}
