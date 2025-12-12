package tp1.MMI3.TP1.dto;

import lombok.Data;

@Data
public class AuthorDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private Integer birthYear;
}