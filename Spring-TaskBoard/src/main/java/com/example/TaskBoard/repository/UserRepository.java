package com.example.TaskBoard.repository;

import com.example.TaskBoard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findUserByEmail(String email);
    void deleteUserByEmail(String email);
    Optional<User> findUserByEmailAndPassword(String email, String password);
}
