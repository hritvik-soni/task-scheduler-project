package com.task_scheduler_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableRetry
@EnableAsync
public class TaskSchedulerProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskSchedulerProjectApplication.class, args);
	}

}
