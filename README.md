# 🗓️ Task Scheduler App

A Spring Boot-based Task Management system with automatic status promotion every 6 hours using a scheduled job and Spring Retry.

---

## 📁 Project Structure

```
src/main/java/com/task_scheduler_project/
├── TaskSchedulerProjectApplication.java
├── TaskController.java
├── Task.java
├── TastRequestDTO.java
├── TaskStatus.java
├── TaskRepository.java
├── TaskStatusScheduler.java
└── TaskService.java

src/main/resources/
├── application.properties
```

---

## 🚀 How to Run

### Prerequisites
- Java 17+
- Maven installed

### Running the app
```bash
git clone https://github.com/hritvik-soni/task-scheduler-project.git
cd task-scheduler-project
./mvnw spring-boot:run
```

---

## 📘 API Documentation (Swagger)

- URL: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## 📘 Live API Documentation (Swagger)

- URL: [https://task-scheduler-project.hotnow.site/swagger-ui/index.html](https://task-scheduler-project.hotnow.site/swagger-ui/index.html)

---

## 🔗 H2 Console (In-memory Database)

- URL: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- JDBC URL: `jdbc:h2:mem:taskdb`
- Username: `sa`
- Password: *(leave blank)*

---

## 🛠️ REST API Endpoints

### ➕ Create Task
**POST** `/tasks`

#### Request:
```json
{
  "title": "Task 1",
  "description": "Task 1 description"
}
```
| Field        | Type   | Required | Description         |
|--------------|--------|----------|---------------------|
| `title`      | String | ✅ Yes   | Title of the task   |
| `description`| String | ❌ No    | Description (optional) |

#### Response:
```json
{
  "id": 1,
  "title": "Task 1",
  "description": "Task 1 description",
  "status": "PENDING",
  "createdAt": "2025-05-22T12:00:00",
  "updatedAt": "2025-05-22T12:00:00"
}
```

---

### 📄 Get All Tasks
**GET** `/tasks`

#### Response:
```json
[
  {
    "id": 1,
    "title": "Task 1",
    "description": "Task 1 description",
    "status": "PENDING",
    "createdAt": "2025-05-22T12:00:00",
    "updatedAt": "2025-05-22T12:00:00"
  }
]
```

---

## ⏰ Scheduled Job - Auto Status Promotion

Runs **every 6 hours** using `@Scheduled(fixedRate = 21600000)`.

### Promotion Flow:
- `PENDING` → `IN_PROGRESS`
- `IN_PROGRESS` → `REVIEW`
- `REVIEW` → `COMPLETED`

**NOTE:** Tasks already marked `COMPLETED` are skipped.

### ✅ Retry Mechanism
- Uses `@Retryable` to retry the promotion **up to 3 times** with **1 second delay** if any failure occurs.

### Example Log Output:
```
[PENDING → IN_PROGRESS]: 5 tasks updated
[IN_PROGRESS → REVIEW]: 3 tasks updated
[REVIEW → COMPLETED]: 2 tasks updated
```

---

## 💡 Technologies Used

- Java 17
- Spring Boot
- Spring Scheduler
- Spring Retry
- Spring Data JPA
- H2 Database
- Lombok
- Swagger (springdoc-openapi)

---

## ✍️ Author

Made with ❤️ by **[Hritvik soni]**  
🔗 [GitHub](https://github.com/hritvik-soni) | [LinkedIn](https://www.linkedin.com/in/hritvik-soni/)

---
