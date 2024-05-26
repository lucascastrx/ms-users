package com.ms.users.infra.adapter.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static jakarta.persistence.FetchType.LAZY;


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "TB_STAKE")
@Entity
public class StakeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    private Long id;

    private String name;
    private double amount;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false)
    private WalletModel wallet;
}
