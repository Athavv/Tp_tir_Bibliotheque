package tp1.MMI3.TP1.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import tp1.MMI3.TP1.model.Category;

import java.time.Year;

@Data
public class BookDTO {
    private Long id;

    @NotBlank(message = "title is required")
    private String title;

    @Min(value = 1450, message = "Year must be at least 1450")
    @Max(value = 2025, message = "Year cannot be in the future")
    private String isbn;

    @Min(value = 1450, message = "year must be at least 1450")
    private Integer year;

    private Category category;

    private Long authorId;

    private String authorFirstName;
    private String authorLastName;
}