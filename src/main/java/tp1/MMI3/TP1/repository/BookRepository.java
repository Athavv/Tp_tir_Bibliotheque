package tp1.MMI3.TP1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tp1.MMI3.TP1.model.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByIsbn(String isbn);

    // Statistiques
    @Query("SELECT b.category, COUNT(b) FROM Book b GROUP BY b.category")
    List<Object[]> countByCategory();

    @Query("SELECT a.firstName, a.lastName, COUNT(b) FROM Book b JOIN b.author a GROUP BY a.id, a.firstName, a.lastName ORDER BY COUNT(b) DESC")
    List<Object[]> findTopAuthors();
}