package tp1.MMI3.TP1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tp1.MMI3.TP1.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
