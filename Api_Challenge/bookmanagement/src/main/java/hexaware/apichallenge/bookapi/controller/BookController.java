package hexaware.apichallenge.bookapi.controller;

import hexaware.apichallenge.bookapi.dto.ApiResponse;
import hexaware.apichallenge.bookapi.dto.BookRequest;
import hexaware.apichallenge.bookapi.entity.Book;
import hexaware.apichallenge.bookapi.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Book>>> getAllBooks() {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        LocalDateTime.now(),
                        200,
                        "Books retrieved successfully",
                        bookService.getAllBooks()
                )
        );
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<ApiResponse<Book>> getBookByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        LocalDateTime.now(),
                        200,
                        "Book retrieved successfully",
                        bookService.getBookByIsbn(isbn)
                )
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Book>> addBook(@Valid @RequestBody BookRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(
                        LocalDateTime.now(),
                        201,
                        "Book added successfully",
                        bookService.addBook(request)
                )
        );
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<ApiResponse<Book>> updateBook(
            @PathVariable String isbn,
            @Valid @RequestBody BookRequest request
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        LocalDateTime.now(),
                        200,
                        "Book updated successfully",
                        bookService.updateBook(isbn, request)
                )
        );
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<ApiResponse<Object>> deleteBook(@PathVariable String isbn) {
        bookService.deleteBook(isbn);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        LocalDateTime.now(),
                        200,
                        "Book deleted successfully",
                        null
                )
        );
    }
}