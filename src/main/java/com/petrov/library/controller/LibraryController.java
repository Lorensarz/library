package com.petrov.library.controller;

import com.petrov.library.db.entity.Book;
import com.petrov.library.db.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class LibraryController {
        private final BookRepository bookRepository;

        @GetMapping
        public ResponseEntity<Page<Book>> getAllBooks(Pageable pageable) {
                Page<Book> books = bookRepository.findAll(pageable);
                return new ResponseEntity<>(books, HttpStatus.OK);
        }

        @GetMapping("/{id}")
        public ResponseEntity<Book> getBookById(@PathVariable Long id) {
                Optional<Book> book = bookRepository.findById(id);
                return book.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        @PostMapping
        public ResponseEntity<Book> createBook(@RequestBody Book book) {
                Book savedBook = bookRepository.save(book);
                return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        }

        @PutMapping("/{id}")
        public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
                Optional<Book> book = bookRepository.findById(id);
                if (book.isPresent()) {
                        Book updatedBook = book.get();
                        updatedBook.setTitle(bookDetails.getTitle());
                        updatedBook.setAuthor(bookDetails.getAuthor());
                        updatedBook.setPublicationYear(bookDetails.getPublicationYear());
                        bookRepository.save(updatedBook);
                        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
                } else {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
        }
        @DeleteMapping("/{id}")
        public ResponseEntity<HttpStatus> deleteBook(@PathVariable Long id) {
                try {
                        bookRepository.deleteById(id);
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } catch (Exception e) {
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
        }
}
