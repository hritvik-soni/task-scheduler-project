package com.task_scheduler_project;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskStatusScheduler {

    private final TaskService taskService;

    public TaskStatusScheduler(TaskService taskService) {
        this.taskService = taskService;
    }

    @Scheduled(fixedRate = 21600000) // 6 hours in milliseconds
    public void updateTaskStatuses() {
        taskService.promoteTaskStatuses();
    }
}

