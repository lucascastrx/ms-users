package com.ms.users.api.dto.wallet.input;

import jakarta.validation.constraints.PositiveOrZero;

public record WalletBalanceDTO(@PositiveOrZero double balance) {
}
