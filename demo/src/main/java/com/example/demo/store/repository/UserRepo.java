package com.example.demo.store.repository;

import com.example.demo.store.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<UserEntity, Long> {

    public UserEntity findUserEntityByName(String name);

}
