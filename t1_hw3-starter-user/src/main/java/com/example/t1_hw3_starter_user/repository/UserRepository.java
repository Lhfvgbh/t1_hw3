package com.example.t1_hw3_starter_user.repository;

import com.example.t1_hw3_starter_user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с таблицей `users`
 */
public interface UserRepository extends JpaRepository<User, Integer> {
}