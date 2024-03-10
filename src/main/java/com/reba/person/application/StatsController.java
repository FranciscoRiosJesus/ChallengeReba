package com.reba.person.application;

import com.reba.person.application.service.StatsService;
import com.reba.person.domain.model.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StatsController {

    @Autowired
    private StatsService statsService;

    @GetMapping("/stats")
    public ResponseEntity<List<Stat>> getStats() {
        List<Stat> stats = statsService.getStats();
        return ResponseEntity.ok(stats);
    }
}
