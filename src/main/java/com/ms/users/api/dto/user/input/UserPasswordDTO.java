package com.ms.users.api.dto.user.input;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserPasswordDTO(@NotBlank String name, @NotBlank String username, @NotBlank @Email String email, @NotBlank String password) {
}
