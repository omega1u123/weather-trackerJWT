package com.example.demo.store.entity;

import com.example.demo.store.RoleEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String password;

    @ManyToOne
    private RoleEntity role;

    public UserEntity(String name, String password, RoleEntity role){
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public UserEntity() {

    }
}
