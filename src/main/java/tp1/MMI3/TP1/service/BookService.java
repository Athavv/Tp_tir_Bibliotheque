package tp1.MMI3.TP1.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tp1.MMI3.TP1.dto.BookDTO;
import tp1.MMI3.TP1.model.Author;
import tp1.MMI3.TP1.model.Book;
import tp1.MMI3.TP1.repository.AuthorRepository;
import tp1.MMI3.TP1.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookDTO createBook(BookDTO bookDTO) {
        if (bookRepository.existsByIsbn(bookDTO.getIsbn())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A book with ISBN " + bookDTO.getIsbn() + " already exists"
            );
        }

        Author author = authorRepository.findById(bookDTO.getAuthorId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Author not found with id: " + bookDTO.getAuthorId()
                ));

        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setIsbn(bookDTO.getIsbn());
        book.setYear(bookDTO.getYear());
        book.setCategory(bookDTO.getCategory());
        book.setAuthor(author);

        Book savedBook = bookRepository.save(book);
        return convertToDTO(savedBook);
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Book not found with id: " + id
                ));
        return convertToDTO(book);
    }

    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Book not found with id: " + id
                ));

        if (!book.getIsbn().equals(bookDTO.getIsbn()) && bookRepository.existsByIsbn(bookDTO.getIsbn())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A book with ISBN " + bookDTO.getIsbn() + " already exists"
            );
        }

        Author author = authorRepository.findById(bookDTO.getAuthorId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Author not found with id: " + bookDTO.getAuthorId()
                ));

        book.setTitle(bookDTO.getTitle());
        book.setIsbn(bookDTO.getIsbn());
        book.setYear(bookDTO.getYear());
        book.setCategory(bookDTO.getCategory());
        book.setAuthor(author);

        Book updatedBook = bookRepository.save(book);
        return convertToDTO(updatedBook);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Book not found with id: " + id
            );
        }
        bookRepository.deleteById(id);
    }

    private BookDTO convertToDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setIsbn(book.getIsbn());
        dto.setYear(book.getYear());
        dto.setCategory(book.getCategory());
        dto.setAuthorId(book.getAuthor().getId());
        dto.setAuthorFirstName(book.getAuthor().getFirstName());
        dto.setAuthorLastName(book.getAuthor().getLastName());
        return dto;
    }
}