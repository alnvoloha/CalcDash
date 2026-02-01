package com.calc.backend.controller;

import com.calc.backend.entity.MathTask;
import com.calc.backend.enums.TaskType;
import com.calc.backend.service.TaskService;
import com.calc.backend.util.UserUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.calc.backend.dto.AnswerRequestDto;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserUtil userUtil;

    public TaskController(TaskService taskService, UserUtil userUtil) {
        this.taskService = taskService;
        this.userUtil = userUtil;
    }

    @GetMapping("/generate")
    public ResponseEntity<List<MathTask>> generateTasks(@RequestParam TaskType type) {
        return ResponseEntity.ok(taskService.generateAndSaveTasks(userUtil.getCurrentUser(), type));
    }

    @PostMapping("/answer")
    public ResponseEntity<Boolean> submitAnswer(@RequestBody AnswerRequestDto dto) {
        boolean correct = taskService.submitAnswer(dto.getTaskId(), dto.getUserAnswer());
        return ResponseEntity.ok(correct);
    }
    @GetMapping("/incorrect")
    public ResponseEntity<List<MathTask>> getIncorrectTasks() {
        return ResponseEntity.ok(taskService.getIncorrectTasks(userUtil.getCurrentUser()));
    }

}
