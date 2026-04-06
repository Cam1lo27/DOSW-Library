package edu.eci.dosw.tdd.persistence.repository.jpa;

import edu.eci.dosw.tdd.persistence.dao.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByUsername(String username);
}