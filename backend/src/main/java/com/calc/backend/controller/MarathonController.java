package com.calc.backend.controller;

import com.calc.backend.dto.MarathonAnswerDto;
import com.calc.backend.enums.TaskDifficulty;
import com.calc.backend.service.MarathonService;
import com.calc.backend.util.UserUtil;
import com.calc.backend.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping({"/api/marathon", "/marathon"})

public class MarathonController {

    private final MarathonService marathonService;
    private final UserUtil userUtil;

    public MarathonController(MarathonService marathonService, UserUtil userUtil) {
        this.marathonService = marathonService;
        this.userUtil = userUtil;
    }

    @GetMapping("/start")
    public ResponseEntity<Map<String, Object>> startMarathon(
            @RequestParam(defaultValue = "MEDIUM") TaskDifficulty difficulty
    ) {
        User user = userUtil.getCurrentUser();
        Map<String, Object> response = marathonService.startMarathon(user, difficulty);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/answer")
    public ResponseEntity<Boolean> submitAnswer(@RequestBody MarathonAnswerDto dto) {
        boolean correct = marathonService.submitAnswer(dto.getTaskId(), dto.getUserAnswer());
        return ResponseEntity.ok(correct);
    }

    @PostMapping("/finish")
    public ResponseEntity<Integer> finish(@RequestBody Map<String, Integer> body) {
        User user = userUtil.getCurrentUser();
        int score = body.getOrDefault("score", 0);
        int record = marathonService.finishMarathon(user, score);
        return ResponseEntity.ok(record);
    }

    @GetMapping("/record")
    public ResponseEntity<Integer> getRecord() {
        User user = userUtil.getCurrentUser();
        return ResponseEntity.ok(user.getMarathonRecord());
    }
}
