package com.ms.users.api.dto.wallet.output;

import com.ms.users.domain.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WalletDTO {
    private Long id;
    private double balance;
    private User user;
}
