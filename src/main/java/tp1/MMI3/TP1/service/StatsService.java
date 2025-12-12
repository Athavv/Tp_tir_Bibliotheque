package tp1.MMI3.TP1.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tp1.MMI3.TP1.model.Category;
import tp1.MMI3.TP1.repository.BookRepository;

import java.util.*;

@Service
@AllArgsConstructor
public class StatsService {

    private final BookRepository bookRepository;

    public Map<String, Long> getBooksPerCategory() {
        Map<String, Long> result = new HashMap<>();
        List<Object[]> stats = bookRepository.countByCategory();

        for (Object[] row : stats) {
            Category category = (Category) row[0];
            Long count = (Long) row[1];
            result.put(category.name(), count);
        }

        return result;
    }

    public List<Map<String, Object>> getTopAuthors(int limit) {
        List<Map<String, Object>> result = new ArrayList<>();
        List<Object[]> stats = bookRepository.findTopAuthors();

        int count = 0;
        for (Object[] row : stats) {
            if (count >= limit) break;

            Map<String, Object> author = new HashMap<>();
            author.put("firstName", row[0]);
            author.put("lastName", row[1]);
            author.put("bookCount", row[2]);
            result.add(author);

            count++;
        }

        return result;
    }
}