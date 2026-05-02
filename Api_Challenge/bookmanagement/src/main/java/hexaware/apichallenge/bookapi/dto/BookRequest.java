package hexaware.apichallenge.bookapi.dto;


import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
    private String title;

    @NotBlank(message = "Author is required")
    @Size(min = 2, max = 100, message = "Author name must be between 2 and 100 characters")
    private String author;

    @NotBlank(message = "ISBN is required")
    @Size(min = 3, max = 30, message = "ISBN must be between 3 and 30 characters")
    private String isbn;

    @Min(value = 1000, message = "Publication year must be valid")
    @Max(value = 2026, message = "Publication year cannot be in the future")
    private int publicationYear;
}
