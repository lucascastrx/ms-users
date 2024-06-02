package com.ms.users.UT.service;

import com.ms.users.domain.adapter.service.UserService;
import com.ms.users.domain.model.User;
import com.ms.users.infra.adapter.repository.UserRepository;
import com.ms.users.infra.producer.UserProducer;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserProducer userProducer;
    @InjectMocks
    private UserService userService;

    @Test
    public void shouldReturnUserWhenSavingWithUniqueEmail(){

        var user = new User();
        user.setName("Lucas");
        user.setUsername("Lucas");
        user.setEmail("lucas@gmail.com");
        user.setPassword("lucas");

        var savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName("Lucas");
        savedUser.setUsername("Lucas");
        savedUser.setEmail("lucas@gmail.com");
        savedUser.setPassword("lucas");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        var testUser = userService.addUser(user);
        assertThat(testUser).isNotNull();
    }

    @Test
    public void shouldFailWhenSavingUserWithRepeatedEmail(){
        var user = new User();
        user.setName("Lucas");
        user.setUsername("Lucas");
        user.setEmail("lucas@gmail.com");
        user.setPassword("lucas");

        var savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName("Lucas");
        savedUser.setUsername("Lucas");
        savedUser.setEmail("lucas@gmail.com");
        savedUser.setPassword("lucas");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(savedUser));

        IllegalStateException error = assertThrows(IllegalStateException.class,
                ()-> userService.addUser(user));

        assertThat(error).isNotNull();
    }

    @Test
    public void shouldReturnUserWhenFetchingWithValidId(){
        var savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName("Lucas");
        savedUser.setUsername("Lucas");
        savedUser.setEmail("lucas@gmail.com");
        savedUser.setPassword("lucas");

        when(userRepository.findById(1L)).thenReturn(Optional.of(savedUser));

        var testUser = userService.findById(1L);

        assertThat(testUser).isNotNull();
        assertThat(testUser.getName()).isEqualTo("Lucas");
    }

    @Test
    public void shouldFailWhenFetchingWithInvalidId(){
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException error = assertThrows(EntityNotFoundException.class,
                ()-> userService.findById(1L));

        assertThat(error).isNotNull();

    }

    @Test
    public void shouldFailWhenChangePasswordWithIncorrectCurrent(){
        var savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName("Lucas");
        savedUser.setUsername("Lucas");
        savedUser.setEmail("lucas@gmail.com");
        savedUser.setPassword("lucas");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(savedUser));

        DataIntegrityViolationException error = assertThrows(DataIntegrityViolationException.class, () -> {
            userService.changePassword(1L, "wrongPassword", "newPassword");
        });

        assertThat(error).isNotNull();
    }

}
