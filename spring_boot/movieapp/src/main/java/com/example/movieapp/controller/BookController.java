package com.example.movieapp.controller;

import com.example.movieapp.model.Book;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/book")

public class BookController {
    private List<Book> books = new ArrayList<>();

    public BookController() {
        books.add(new Book(1, "Java", "James", 500));
        books.add(new Book(2, "Spring", "Rod", 700));
    }

    @PostMapping("/add")
    public String addBook(@RequestBody Book book) {
        for (Book b : books) {
            if (b.getBookId() == book.getBookId()) {
                return "Book ID already exists";
            }
        }
        books.add(book);
        return "Book added successfully";
    }

    @GetMapping("/all")
    public List<Book> getAllBooks() {
        return books;
    }

    @GetMapping("/{id}")
    public Object getBookById(@PathVariable int id) {
        for (Book b : books) {
            if (b.getBookId() == id) {
                return b;
            }
        }
        return "Book not found";
    }

    @PutMapping("/update/{id}")
    public String updateBook(@PathVariable int id, @RequestBody Book updatedBook) {
        for (Book b : books) {
            if (b.getBookId() == id) {
                b.setBookName(updatedBook.getBookName());
                b.setAuthor(updatedBook.getAuthor());
                b.setPrice(updatedBook.getPrice());
                return "Book updated successfully";
            }
        }
        return "Book not found";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBook(@PathVariable int id) {
        Iterator<Book> iterator = books.iterator();
        while (iterator.hasNext()) {
            Book b = iterator.next();
            if (b.getBookId() == id) {
                iterator.remove();
                return "Book deleted successfully";
            }
        }
        return "Book not found";
    }

    @GetMapping("/price/500")
    public List<Book> getBooksPriceGreaterThan500() {
        List<Book> result = new ArrayList<>();
        for (Book b : books) {
            if (b.getPrice() > 500) {
                result.add(b);
            }
        }
        return result;
    }

    public int getBookCount() {
        return books.size();
    }
}



