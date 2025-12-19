package com.skyeagle.mcptest1.tools;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;

import com.skyeagle.mcptest1.model.Book;
import com.skyeagle.mcptest1.service.BookService;

@Component
public class BooksTool {

  private static final Logger logger = LoggerFactory.getLogger(BooksTool.class);

  private BookService bookService;

  public BooksTool(BookService bookService) {
    this.bookService = bookService;
  }

  @McpTool(name = "get_all_books", description = "Get information about all available books.")
  public List<Book> getAllBooks() {
    logger.info("BooksTool: Getting all books");
    return bookService.getAllBooks();
  }

  @McpTool(name = "get_book_info", description = "Get detailed information about a book by its ID.")
  public Book getBookInfo(
      @McpToolParam(description = "The unique identifier of the book.") String bookId) {
    logger.info("BooksTool: Getting book info for bookId: {}", bookId);
    return bookService.getBookInfo(bookId);
  }

  @McpTool(name = "get_book_by_name", description = "Get detailed information about a book by its title.")
  public Book getBookByName(
      @McpToolParam(description = "The title of the book.") String title) {
    logger.info("BooksTool: Getting book info for title: {}", title);
    return bookService.getBookByName(title);
  }

  @McpTool(name = "add_book", description = "Add a new book to the collection.")
  public Book addBook(
      @McpToolParam(description = "The book to add.") Book book) {
    logger.info("BooksTool: Adding new book: {}", book.getTitle());
    bookService.addBook(book);
    return book;
  }

}
