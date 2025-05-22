package com.task_scheduler_project;

import lombok.Getter;

@Getter
public enum TaskStatus {
    PENDING,
    IN_PROGRESS,
    REVIEW,
    COMPLETED
}
