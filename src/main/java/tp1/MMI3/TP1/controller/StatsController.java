package tp1.MMI3.TP1.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tp1.MMI3.TP1.service.StatsService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stats")
@AllArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @GetMapping("/books-per-category")
    public Map<String, Long> getBooksPerCategory() {
        return statsService.getBooksPerCategory();
    }

    @GetMapping("/top-authors")
    public List<Map<String, Object>> getTopAuthors(@RequestParam(defaultValue = "3") int limit) {
        return statsService.getTopAuthors(limit);
    }
}