package com.skyeagle.mcptest1.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.skyeagle.mcptest1.model.Book;

@Service
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    private List<Book> books = List.of(
        new Book("1", "1984", "George Orwell", "Secker & Warburg", 1949, "1234567890", "Dystopian", 9.99),
        new Book("2", "To Kill a Mockingbird", "Harper Lee", "J.B. Lippincott & Co.", 1960, "0987654321", "Fiction", 7.99)
    );

    public Book getBookInfo(String bookId) {
        logger.info("Fetching book info for bookId: {}", bookId);
        Book book = books.stream()
            .filter(b -> b.getId().equals(bookId))
            .findFirst()
            .orElse(null);

        return book;
    }

    public Book getBookByName(String title) {
        logger.info("Fetching book info for title: {}", title);
        Book book = books.stream()
            .filter(b -> b.getTitle().equalsIgnoreCase(title))
            .findFirst()
            .orElse(null);

        return book;
    }

    public List<Book> getAllBooks() {
        logger.info("Fetching all books");
        return books;
    }

    public Book addBook(Book book) {
        logger.info("Adding new book: {}", book);
        books.add(book);
        return book;
    }

    public boolean deleteBook(String bookId) {
        logger.info("Deleting book with bookId: {}", bookId);
        return books.removeIf(b -> b.getId().equals(bookId));
    }
    
}
