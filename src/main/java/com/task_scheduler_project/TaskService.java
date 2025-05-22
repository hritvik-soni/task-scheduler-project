package com.task_scheduler_project;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public ResponseEntity<?> createTask(TaskRequestDTO payload) {
        try {
            log.info("Received request to create task");
            if (payload.getTitle().isBlank()) {
                return ResponseEntity.badRequest().body("Please provide a valid title");
            }
            Task task = new Task();
            task.setTitle(payload.getTitle());
            task.setDescription(payload.getDescription());
            task.setStatus(TaskStatus.PENDING);
            task = taskRepository.save(task);

            return ResponseEntity.ok(task);
        } catch (Exception e) {
            log.error("Exception while creating task :: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Error while processing your request. please try again.");
        }
    }

    public ResponseEntity<?> getAllTasks(long taskId) {
        try {
            log.info("Received request to fetch tasks");
            if (taskId > 0) {
                log.info("searching task by id : {}", taskId);
                return ResponseEntity.ok(taskRepository.findById(taskId));
            }
            log.info("searching all tasks");
            return ResponseEntity.ok(taskRepository.findAll());
        } catch (Exception e) {
            log.error("Exception while fetching tasks :: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Error while processing your request. please try again.");
        }
    }

    @Retryable(
            value = {Exception.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 2)
    )
    public void promoteTaskStatuses() {
        try {
            log.info("Scheduled task: promoting task statuses...");

            List<Task> tasks = taskRepository.findByStatusNot(TaskStatus.COMPLETED);
            if (tasks.isEmpty()) {
                log.info("No tasks found to promote.");
                return;
            }

            int pendingToInProgress = 0;
            int inProgressToReview = 0;
            int reviewToCompleted = 0;

            for (Task task : tasks) {
                switch (task.getStatus()) {
                    case PENDING -> {
                        task.setStatus(TaskStatus.IN_PROGRESS);
                        pendingToInProgress++;
                    }
                    case IN_PROGRESS -> {
                        task.setStatus(TaskStatus.REVIEW);
                        inProgressToReview++;
                    }
                    case REVIEW -> {
                        task.setStatus(TaskStatus.COMPLETED);
                        reviewToCompleted++;
                    }
                    default -> {
                    }
                }
            }

            taskRepository.saveAll(tasks);

            log.info("Task status promotion summary:");
            log.info("PENDING → IN_PROGRESS: {}", pendingToInProgress);
            log.info("IN_PROGRESS → REVIEW: {}", inProgressToReview);
            log.info("REVIEW → COMPLETED: {}", reviewToCompleted);

        } catch (Exception e) {
            log.error("Exception while promoting task statuses", e);
            throw e;
        }
    }
}