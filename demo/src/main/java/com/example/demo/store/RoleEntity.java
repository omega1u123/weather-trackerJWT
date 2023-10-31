package com.example.demo.store;

import com.example.demo.store.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany
    private List<UserEntity> users;

    public RoleEntity(String name){
        this.name = name;
    }

    public RoleEntity() {

    }
}
