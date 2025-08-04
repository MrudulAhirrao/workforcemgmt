# Workforce Management API

This project is a fully functional Workforce Management API built with **Spring Boot**. It was developed as part of a backend engineering challenge, starting from a single-file concept and evolving into a well-structured, multi-layered application. The API allows managers to create, assign, and track tasks for their employees.

## Tech Stack 🛠️

  * **Language:** Java 17
  * **Framework:** Spring Boot 3.0.4
  * **Build Tool:** Gradle
  * **Dependencies:**
      * Spring Web
      * Lombok
      * MapStruct
  * **Database:** In-memory data store (Java Collections)

-----

## How to Run Locally 🚀

1.  Ensure you have **Java 17** and **Gradle** installed.
2.  Clone the repository.
3.  Navigate to the project's root directory in your terminal.
4.  Run the application using the Gradle wrapper:
    ```bash
    # On macOS/Linux
    ./gradlew bootRun

    # On Windows
    ./gradlew.bat bootRun
    ```
5.  The application will start on `http://localhost:8080`.

-----

## Part 1: Bug Fixes 🐞

Two critical bugs in the initial codebase were identified and resolved.

### Bug 1: Task Re-assignment Creates Duplicates

  * **Problem:** When a task was reassigned to a new employee, the old task remained active, causing duplicate entries.

  * **Fix:** The logic was updated to ensure that when a reassignment occurs, the new task is assigned, and any previous active tasks for that same reference are marked as **`CANCELLED`**. This maintains a clean state with only one active task per job.

  * **How to Test:**

    1.  Send a `POST` request to reassign tasks for `reference_id: 201`.
    2.  Fetch the individual tasks to verify one is updated and the other is cancelled.


    
    # Step 1: Reassign the task
    <img width="1301" height="836" alt="image" src="https://github.com/user-attachments/assets/acd4ef72-5ab6-4d54-b746-a547fad01967" />


    # Step 2: Verify by fetching task ID 4 & 5, its status should be CANCELLED

    <img width="1145" height="822" alt="image" src="https://github.com/user-attachments/assets/2a8acfc4-7fcd-4cc5-8d19-d97619aebd61" />

    <img width="1235" height="846" alt="image" src="https://github.com/user-attachments/assets/5fb92cc0-63a5-4880-917f-6183cebd410b" />


### Bug 2: Cancelled Tasks Clutter the View

  * **Problem:** The endpoint for fetching tasks for an employee was returning `CANCELLED` tasks, which cluttered the user's active work list.
  * **Fix:** A filter was added to the service logic to explicitly exclude any tasks with a `CANCELLED` status from the response.
  * **How to Test:** Send a `POST` request to fetch tasks for users `1` and `2`. The cancelled task (ID 6) will be absent from the results.
   
  <img width="1254" height="508" alt="image" src="https://github.com/user-attachments/assets/ef3037c6-bb72-4e55-8944-a4e0ec4ae678" />
  <img width="1814" height="641" alt="image" src="https://github.com/user-attachments/assets/bc4cf601-fecc-47f8-8f29-8d920ac7c60f" />
  <img width="1340" height="524" alt="image" src="https://github.com/user-attachments/assets/0c3867cc-7d7e-4e0f-826e-9f3b8beae3fe" />


-----

## Part 2: New Features ✨

The API was enhanced with three highly requested features.

### Feature 1: "Smart" Daily Task View

  * **User Need:** A true "today's work" view that shows all open tasks, including older ones that are still active.
  * **Implementation:** The task fetching logic was enhanced to return all **`ASSIGNED`** or **`STARTED`** tasks that either started within the requested date range OR started before the range but are still open.
  * **How to Test:** The test for Bug \#2 above also demonstrates this feature, as it correctly fetches tasks based on the new logic and date range.
<img width="1241" height="827" alt="image" src="https://github.com/user-attachments/assets/a6008988-97fc-4a72-9675-41d16ed8c4ea" />
<img width="1232" height="827" alt="image" src="https://github.com/user-attachments/assets/54f35132-7dd0-4ba0-8ebb-f5cb85f9508f" />
<img width="1239" height="817" alt="image" src="https://github.com/user-attachments/assets/dfb357da-61de-46e3-bd5d-5f351317d971" />
<img width="1234" height="838" alt="image" src="https://github.com/user-attachments/assets/21126c3e-6b8c-4037-ab1b-8e49079cb91e" />
<img width="1204" height="312" alt="image" src="https://github.com/user-attachments/assets/8b5addeb-6ccc-4dd2-a133-8b3233e50802" />



### Feature 2: Task Priority

  * **User Need:** The ability for managers to set and filter tasks by priority.
  * **Implementation:** A `priority` field (`HIGH`, `MEDIUM`, `LOW`) was added to the `Task` model. Two new endpoints were created: one to update a task's priority and one to fetch all tasks of a specific priority.
  * **How to Test:**
    <img width="732" height="823" alt="image" src="https://github.com/user-attachments/assets/1a7723b1-a709-42bb-ac28-ffeb156f220e" />
    <img width="1229" height="829" alt="image" src="https://github.com/user-attachments/assets/ded9e857-e789-4d4a-922f-d9ac3f43dbd9" />
    <img width="935" height="809" alt="image" src="https://github.com/user-attachments/assets/fdee3ede-d273-4a51-a6f8-db0ddc2997c4" />
    

    # Example 2: Fetch all tasks with MEDIUM priority
    <img width="1204" height="790" alt="image" src="https://github.com/user-attachments/assets/f4c9d26a-1e35-462a-bf01-c0a387cc910d" />


### Feature 3: Task Comments & Activity History

  * **User Need:** A full audit trail for each task, including automatic logs and manual user comments.
  * **Implementation:** An event system was created to log key activities (creation, status change, etc.) automatically. An endpoint was also added to allow users to post free-text comments. When a single task is fetched, its complete history and comments are included, sorted chronologically.
  * **How to Test:**
    <img width="1263" height="905" alt="image" src="https://github.com/user-attachments/assets/d824c8f0-8474-41a7-9c12-dcc26ec4d889" />


    # Step 2: Add a comment to task 13
    <img width="1227" height="428" alt="image" src="https://github.com/user-attachments/assets/04fdb897-b998-4da3-a1d1-0237a1515b6b" />
    <img width="1208" height="432" alt="image" src="https://github.com/user-attachments/assets/9eb6714f-bb42-4a82-b8ca-462cc6c6a003" />
    <img width="770" height="496" alt="image" src="https://github.com/user-attachments/assets/287966d6-9c96-46da-8db8-fd2fdf587be4" />




    # Step 3: Fetch task 13 to see the full, sorted history
    <img width="1253" height="824" alt="image" src="https://github.com/user-attachments/assets/f6319fc5-2b26-4cd8-ade2-35df40a8c640" />


-----

## API Endpoints Summary

| Method | Endpoint                             | Description                                 |
| :----- | :----------------------------------- | :------------------------------------------ |
| `POST` | `/task-mgmt/create`                  | Creates one or more new tasks.              |
| `POST` | `/task-mgmt/update`                  | Updates the status or description of tasks. |
| `POST` | `/task-mgmt/assign-by-ref`           | Re-assigns tasks based on a reference ID.   |
| `POST` | `/task-mgmt/fetch-by-date/v2`        | Fetches tasks for users in a date range.    |
| `GET`  | `/task-mgmt/{id}`                    | Fetches a single task by its ID.            |
| `PATCH`| `/task-mgmt/{id}/priority`           | Updates the priority of a single task.      |
| `GET`  | `/task-mgmt/priority/{priority}`     | Fetches all tasks of a specific priority.   |
| `POST` | `/task-mgmt/{id}/comments`           | Adds a new comment to a task.               |
