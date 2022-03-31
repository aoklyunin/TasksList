package com.example.demo.repository;

import com.example.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий пользователей
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Поиск пользователя по имени
     *
     * @param username имя
     * @return пользователь
     */
    User findByUsername(String username);
}