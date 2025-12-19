package com.skyeagle.mcptest1.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.skyeagle.mcptest1.model.Book;

@Service
public class BookService {

  private static final Logger logger = LoggerFactory.getLogger(BookService.class);

  private List<Book> books;

  @PostConstruct
  public void initializeBooks() {
    books = new ArrayList<>();
    books.add(new Book("1", "1984", "George Orwell", "Secker & Warburg", 1949, "1234567890", "Dystopian", 9.99));
    books.add(new Book("2", "To Kill a Mockingbird", "Harper Lee", "J.B. Lippincott & Co.", 1960, "0987654321",
        "Fiction", 7.99));
    books.add(new Book("3", "The Great Gatsby", "F. Scott Fitzgerald", "Charles Scribner's Sons", 1925,
        "1122334455", "Classic", 10.99));
    books.add(new Book("4", "Pride and Prejudice", "Jane Austen", "T. Egerton", 1813,
        "2233445566", "Romance", 8.99));
    books.add(new Book("5", "The Hobbit", "J.R.R. Tolkien", "George Allen & Unwin", 1937,
        "3344556677", "Fantasy", 12.99));
    books.add(new Book("6", "Moby Dick", "Herman Melville", "Harper & Brothers", 1851,
        "4455667788", "Adventure", 11.50));
    logger.info("Books initialized: {} books loaded", books.size());
  }

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
