package com.ms.users.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "TB_USER")
@Entity
public class User {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    private Long id;

    private String name;
    private String username;
    @EqualsAndHashCode.Include
    private String email;
    private String password;

    public boolean verifyPassword(String newPassword){
        return getPassword().equals(newPassword);
    }
}
