package com.ms.users.api.controller;

import com.ms.users.api.assembler.MapperDTO;
import com.ms.users.api.dto.user.input.PasswordDTO;
import com.ms.users.api.dto.user.input.UserInputDTO;
import com.ms.users.api.dto.user.input.UserPasswordDTO;
import com.ms.users.api.dto.user.output.UserDTO;
import com.ms.users.domain.model.User;
import com.ms.users.domain.port.service.UserServicePort;
import com.ms.users.infra.adapter.repository.UserRepositoryAccess;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepositoryAccess userRepository;
    private final UserServicePort userService;
    private final MapperDTO mapper;

    public UserController(UserRepositoryAccess userRepository, UserServicePort userService, MapperDTO mapper) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.mapper = mapper;
    }


    @GetMapping
    public List<UserDTO> list(){
        return mapper.toCollection(userRepository.findAll(), UserDTO.class);
    }

    @GetMapping("/{userId}")
    public UserDTO findById(@PathVariable Long userId){
        return mapper.transform(userService.findById(userId), UserDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO addUser(@RequestBody @Valid UserPasswordDTO userPasswordDTO){
        var user = mapper.transform(userPasswordDTO, User.class);
        user = userService.addUser(user);
        return mapper.transform(user, UserDTO.class);
    }

    @PutMapping("/{userId}")
    public UserDTO updateUser(@PathVariable Long userId, @RequestBody @Valid UserInputDTO userInputDTO){
        var user = userService.findById(userId);
        mapper.copyToDomain(userInputDTO, user);
        user = userService.addUser(user);
        return mapper.transform(user, UserDTO.class);
    }

    @PutMapping("/{userId}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@PathVariable Long userId, @RequestBody @Valid PasswordDTO passwordDTO){
        userService.changePassword(userId, passwordDTO.currentPassword(), passwordDTO.newPassword());
    }
}

/**
 * TODO: Reiforce username duplicated validation like on email
 */
