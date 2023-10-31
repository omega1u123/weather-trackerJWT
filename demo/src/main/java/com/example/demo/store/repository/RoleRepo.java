package com.example.demo.store.repository;

import com.example.demo.store.RoleEntity;
import com.example.demo.store.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends CrudRepository<RoleEntity, Long> {
    public RoleEntity findRoleEntityByUsers(UserEntity user);
    public RoleEntity findRoleEntityByName(String name);
}
