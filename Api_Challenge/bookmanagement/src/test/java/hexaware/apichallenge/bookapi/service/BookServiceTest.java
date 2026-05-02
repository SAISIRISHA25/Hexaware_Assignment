package hexaware.apichallenge.bookapi.service;

import hexaware.apichallenge.bookapi.dto.BookRequest;
import hexaware.apichallenge.bookapi.entity.Book;
import hexaware.apichallenge.bookapi.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    BookRepository bookRepository = Mockito.mock(BookRepository.class);
    BookService bookService = new BookService(bookRepository);

    @Test
    void testGetBookByIsbn() {
        Book book = new Book(1L, "Java", "James Gosling", "ISBN101", 2020);

        when(bookRepository.findByIsbn("ISBN101"))
                .thenReturn(Optional.of(book));

        Book result = bookService.getBookByIsbn("ISBN101");

        assertEquals("Java", result.getTitle());
        assertEquals("ISBN101", result.getIsbn());
    }

    @Test
    void testAddBook() {
        BookRequest request = new BookRequest();
        request.setTitle("Spring Boot Guide");
        request.setAuthor("Rod Johnson");
        request.setIsbn("ISBN102");
        request.setPublicationYear(2023);

        Book savedBook = new Book(
                1L,
                request.getTitle(),
                request.getAuthor(),
                request.getIsbn(),
                request.getPublicationYear()
        );

        when(bookRepository.existsByIsbn("ISBN102")).thenReturn(false);
        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        Book result = bookService.addBook(request);

        assertEquals("Spring Boot Guide", result.getTitle());
        assertEquals("ISBN102", result.getIsbn());

        verify(bookRepository, times(1)).save(any(Book.class));
    }
}