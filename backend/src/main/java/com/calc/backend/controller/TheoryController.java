package com.calc.backend.controller;

import com.calc.backend.service.TheoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/theory")
@RequiredArgsConstructor
public class TheoryController {

    private final TheoryService theoryService;

    @GetMapping("/{topic}")
    public String getTheoryByPath(@PathVariable String topic) {
        return theoryService.getTheory(topic);
    }

    @GetMapping
    public String getTheory(@RequestParam String topic) {
        return theoryService.getTheory(topic);
    }
}
