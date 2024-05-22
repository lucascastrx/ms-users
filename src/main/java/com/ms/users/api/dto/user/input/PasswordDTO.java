package com.ms.users.api.dto.user.input;

import jakarta.validation.constraints.NotBlank;

public record PasswordDTO(@NotBlank String currentPassword, @NotBlank String newPassword) {
}
