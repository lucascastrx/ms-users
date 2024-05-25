package com.ms.users.infra.adapter.model;

import com.ms.users.domain.model.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "TB_WALLET")
@Entity
public class WalletModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    private Long id;

    private double balance;

    @OneToOne(orphanRemoval = true)
    private UserModel user;
}
