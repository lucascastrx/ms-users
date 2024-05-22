package com.ms.users.api.dto.user.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserInputDTO(@NotBlank String name, @NotBlank String username, @NotBlank @Email String email) {
}
