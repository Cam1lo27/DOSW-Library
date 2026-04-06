package edu.eci.dosw.tdd.persistence.repository.jpa;

import edu.eci.dosw.tdd.persistence.dao.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookJpaRepository extends JpaRepository<BookEntity, String> {
}