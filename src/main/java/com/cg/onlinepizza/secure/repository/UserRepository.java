package com.cg.onlinepizza.secure.repository;
import org.springframework.data.repository.CrudRepository;


import com.cg.onlinepizza.secure.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
}