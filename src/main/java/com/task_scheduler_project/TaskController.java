package com.task_scheduler_project;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody TaskRequestDTO payload) {
        return taskService.createTask(payload);
    }

    @GetMapping
    public ResponseEntity<?> getAllTasks(@RequestParam(required = false, defaultValue = "0") long taskId) {
        return taskService.getAllTasks(taskId);
    }
}