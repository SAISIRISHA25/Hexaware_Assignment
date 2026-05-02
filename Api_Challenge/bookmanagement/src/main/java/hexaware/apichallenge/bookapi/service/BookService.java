package hexaware.apichallenge.bookapi.service;

import hexaware.apichallenge.bookapi.dto.BookRequest;
import hexaware.apichallenge.bookapi.entity.Book;
import hexaware.apichallenge.bookapi.exception.DuplicateResourceException;
import hexaware.apichallenge.bookapi.exception.ResourceNotFoundException;
import hexaware.apichallenge.bookapi.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ISBN: " + isbn));
    }

    public Book addBook(BookRequest request) {

        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new DuplicateResourceException("Book already exists with ISBN: " + request.getIsbn());
        }

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPublicationYear(request.getPublicationYear());

        return bookRepository.save(book);
    }

    public Book updateBook(String isbn, BookRequest request) {

        Book existingBook = getBookByIsbn(isbn);

        if (!isbn.equals(request.getIsbn()) && bookRepository.existsByIsbn(request.getIsbn())) {
            throw new DuplicateResourceException("Another book already exists with ISBN: " + request.getIsbn());
        }

        existingBook.setTitle(request.getTitle());
        existingBook.setAuthor(request.getAuthor());
        existingBook.setIsbn(request.getIsbn());
        existingBook.setPublicationYear(request.getPublicationYear());

        return bookRepository.save(existingBook);
    }

    public void deleteBook(String isbn) {
        Book book = getBookByIsbn(isbn);
        bookRepository.delete(book);
    }
}