package com.ms.users.api.dto.stake.output;

import com.ms.users.domain.model.Wallet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StakeDTO {

    private Long id;

    private String name;
    private double amount;

    private Wallet wallet;
}
