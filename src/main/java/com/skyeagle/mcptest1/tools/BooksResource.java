package com.skyeagle.mcptest1.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springaicommunity.mcp.annotation.McpResource;
import org.springframework.stereotype.Component;

import com.skyeagle.mcptest1.model.Book;
import com.skyeagle.mcptest1.service.BookService;

@Component
public class BooksResource {

    private static final Logger logger = LoggerFactory.getLogger(BooksResource.class);

    private BookService bookService;

    public BooksResource(BookService bookService) {
        this.bookService = bookService;
    }

    @McpResource(
        uri = "book://catalog", 
        name = "Book Catalog", 
        description = "A comprehensive catalog of all available books with detailed information",
        mimeType = "application/json"
    )
    public String getBookCatalog() {
        logger.info("BooksResource: Fetching book catalog");
        return formatBookCatalog(bookService.getAllBooks());
    }

    @McpResource(
        uri = "book://learning-guide", 
        name = "Book Learning Guide", 
        description = "A guide to help you learn about classic literature with reading recommendations and insights",
        mimeType = "text/markdown"
    )
    public String getLearningGuide() {
        logger.info("BooksResource: Fetching learning guide");
        return """
                # Classic Literature Learning Guide
                
                ## Available Books in Our Collection
                
                ### 1984 by George Orwell
                **Genre:** Dystopian
                **Key Themes:**
                - Totalitarianism and surveillance
                - Control of information and language
                - Individual freedom vs. state power
                
                **Why Read It:**
                This seminal work explores the dangers of authoritarian government and remains 
                remarkably relevant in today's digital age. Perfect for understanding political 
                philosophy and social commentary.
                
                **Learning Objectives:**
                - Understand dystopian literature
                - Analyze political allegory
                - Explore themes of power and control
                
                ---
                
                ### To Kill a Mockingbird by Harper Lee
                **Genre:** Fiction
                **Key Themes:**
                - Racial injustice and prejudice
                - Moral growth and coming of age
                - Courage and compassion
                
                **Why Read It:**
                A powerful story about justice, morality, and human dignity set in the American 
                South. Essential reading for understanding social issues and ethical development.
                
                **Learning Objectives:**
                - Examine social justice themes
                - Analyze character development
                - Understand historical context of the Civil Rights era
                
                ---
                
                ## Reading Recommendations
                
                1. **For Political Philosophy:** Start with *1984*
                2. **For Social Justice:** Begin with *To Kill a Mockingbird*
                3. **For Complete Understanding:** Read both books to compare different approaches to social commentary
                
                ## Discussion Questions
                
                - How do these classics remain relevant today?
                - What lessons can we apply to modern society?
                - How do the authors use fiction to convey important messages?
                
                ## Next Steps
                
                Use the `get_book_info` tool to fetch detailed information about a specific book,
                or use `get_all_books` to see the complete catalog.
                """;
    }

    @McpResource(
        uri = "book://reading-tips", 
        name = "Effective Reading Tips", 
        description = "Tips and strategies for effective reading and comprehension of classic literature",
        mimeType = "text/plain"
    )
    public String getReadingTips() {
        logger.info("BooksResource: Fetching reading tips");
        return """
                EFFECTIVE READING TIPS FOR CLASSIC LITERATURE
                
                1. BEFORE YOU START
                   - Research the historical context
                   - Understand the author's background
                   - Set reading goals
                
                2. WHILE READING
                   - Take notes on key themes
                   - Mark unfamiliar words
                   - Pause to reflect on important passages
                   - Consider the author's perspective
                
                3. ACTIVE READING TECHNIQUES
                   - Ask questions as you read
                   - Make predictions about plot developments
                   - Connect themes to real-world situations
                   - Identify literary devices
                
                4. AFTER READING
                   - Write a summary in your own words
                   - Discuss with others or join a book club
                   - Research critical analyses
                   - Apply lessons to your own life
                
                5. FOR DEEPER UNDERSTANDING
                   - Re-read challenging sections
                   - Compare with similar works
                   - Explore the author's other writings
                   - Study the time period in detail
                
                Remember: Classic literature rewards patient, thoughtful reading.
                Don't rush - let the ideas sink in and evolve over time.
                """;
    }

    private String formatBookCatalog(java.util.List<Book> books) {
        StringBuilder catalog = new StringBuilder("{\n  \"books\": [\n");
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            catalog.append("    {\n");
            catalog.append("      \"id\": \"").append(book.getId()).append("\",\n");
            catalog.append("      \"title\": \"").append(book.getTitle()).append("\",\n");
            catalog.append("      \"author\": \"").append(book.getAuthor()).append("\",\n");
            catalog.append("      \"publisher\": \"").append(book.getPublisher()).append("\",\n");
            catalog.append("      \"yearPublished\": ").append(book.getYearPublished()).append(",\n");
            catalog.append("      \"isbn\": \"").append(book.getIsbn()).append("\",\n");
            catalog.append("      \"genre\": \"").append(book.getGenre()).append("\",\n");
            catalog.append("      \"price\": ").append(book.getPrice()).append("\n");
            catalog.append("    }");
            if (i < books.size() - 1) {
                catalog.append(",");
            }
            catalog.append("\n");
        }
        catalog.append("  ]\n}");
        return catalog.toString();
    }
}
