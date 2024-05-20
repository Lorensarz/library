package com.petrov.library.db.repository;

import com.petrov.library.db.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
