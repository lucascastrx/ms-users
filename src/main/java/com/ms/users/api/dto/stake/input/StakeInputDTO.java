package com.ms.users.api.dto.stake.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record StakeInputDTO (@NotBlank String name, @PositiveOrZero double amount){
}
