package com.example.lawson_prevost_tp1.controller;

import com.example.lawson_prevost_tp1.service.StatsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stats")
public class StatsController {

    private final StatsService service;

    public StatsController(StatsService service) {
        this.service = service;
    }

    @GetMapping("/books-per-category")
    public Object getBooksPerCategory() {
        return service.getBooksPerCategory();
    }

    @GetMapping("/top-authors")
    public Object getTopAuthors(@RequestParam(defaultValue = "3") int limit) {
        return service.getTopAuthors(limit);
    }
}
