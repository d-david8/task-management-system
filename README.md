# Red Team: Task Management System

**Overview:**

Task Management System is a comprehensive and user-friendly Task Management System designed specifically for software
development teams. Our application streamlines the task management process, providing a centralized platform for
creating, assigning, tracking progress, and reporting on software development tasks.

**Key Features:**
- User managements
- Task managements
- Comments managements

**Technologies and frameworks used:**

- Java 17
- SpringBoot 3.1.4
- PostgreSQL
- Maven
- Mockito
- Lombok
- Swagger

# The APIs -> [Swagger](http://localhost:8080/swagger-ui/index.html)

# I. Users

## 1. Create user

- Description: create a new user
- Endpoint: `/users`
- Method: `POST`
- Request body:

```json
{
  "firstName": "Jane",
  "lastName": "Doe",
  "email": "jane.doe@mail.com"
}
```

## 2. Get user by id

- Description: Retrieves a user details by id
- Endpoint: `/users/{id}`
- Method: `GET`
- Request: `/users/1`
- Response:

```json
{
  "id": 1,
  "firstName": "Jon",
  "lastName": "Herman",
  "email": "john.herman@mail.ro",
  "currentTask": null,
  "comments": [
    {
      "id": 1,
      "message": "test",
      "createdAt": "2023-11-06T22:09:31.238895",
      "taskId": 1
    },
    {
      "id": 2,
      "message": "test2",
      "createdAt": "2023-11-06T22:13:48.289369",
      "taskId": 1
    }
  ]
}
```

## 3. Get all users

- Description: Retrieves the list of all users
- Endpoint: `/users`
- Method: `GET`
- Request: `/users`
- Response:

```json
[
  {
    "id": 1,
    "firstName": "Jon",
    "lastName": "Herman",
    "email": "abd@abc.ro",
    "currentTask": null,
    "comments": [
      {
        "id": 1,
        "message": "test",
        "createdAt": "2023-11-06T22:09:31.238895",
        "taskId": 1
      },
      {
        "id": 2,
        "message": "test2",
        "createdAt": "2023-11-06T22:13:48.289369",
        "taskId": 1
      }
    ]
  },
  {
    "id": 2,
    "firstName": "Zola",
    "lastName": "Witting",
    "email": "ad@abc.ro",
    "currentTask": null,
    "comments": []
  }
]
```

## 4. Update user

- Description: Update the details of a user by id
- Endpoint: `/users/{id}`
- Method: `PATCH`
- Request body:

```json
{
  "firstName": "Jane",
  "lastName": "Doe",
  "email": "jane.doe@mail.com"
}
```

## 5. Delete user

- Description: Delete a user by id
- Endpoint: `/users/{id}`
- Method: `DELETE`
- Request: `/users/1`

# II. Tasks

## 1. Create task

- Description: Create a new task
- Endpoint: `/tasks`
- Method: `POST`
- Request: `/tasks`
- Request body:

```json
{
  "title": "Create API",
  "description": "create an api that.....",
  "dueDate": "10.02.2023",
  "priority": "HIGH"
}
```

## 2. Get task by id

- Description: Retrieves a task details by id
- Endpoint: `/tasks/{id}`
- Method: `GET`
- Request: `/tasks/1`
- Response:

```json
{
  "id": 1,
  "title": "Task one",
  "description": "to do something",
  "dueDate": "10.02.2023",
  "priority": "HIGH",
  "status": "TODO",
  "progress": 0,
  "userId": null,
  "comments": [
    {
      "id": 4,
      "message": "test2",
      "createdAt": "2023-11-06T23:18:44.602324",
      "userId": 2
    },
    {
      "id": 5,
      "message": "test2",
      "createdAt": "2023-11-06T23:18:45.649786",
      "userId": 2
    }
  ]
}
```

## 3. Get all tasks

- Description: Retrieves the list of all tasks
- Endpoint: `/tasks`
- Method: `GET`
- Request: `/tasks`
- Response:

```json
[
  {
    "id": 2,
    "title": "Task 1",
    "description": "do something",
    "dueDate": "10.02.2023",
    "priority": "HIGH",
    "status": "TODO",
    "progress": 0,
    "userId": null,
    "comments": []
  },
  {
    "id": 1,
    "title": "Task 2",
    "description": "do something else",
    "dueDate": "10.02.2023",
    "priority": "HIGH",
    "status": "DONE",
    "progress": 100,
    "userId": null,
    "comments": [
      {
        "id": 4,
        "message": "test1",
        "createdAt": "2023-11-06T23:18:44.602324",
        "userId": 2
      },
      {
        "id": 5,
        "message": "test2",
        "createdAt": "2023-11-06T23:18:45.649786",
        "userId": 2
      }
    ]
  }
]
```

## 4. Get tasks by due date

- Description: Retrieves a list of tasks details with the requested due date
- Endpoint: `/tasks/byDueDate`
- Query Parameters: `dueDate`
- Method: `GET`
- Request: `/tasks/byDueDate?dueDate=2023-11-01`
- Response:

```json
[
  {
    "id": 2,
    "title": "Future Configuration Agent",
    "description": "Customer",
    "dueDate": "10.02.2023",
    "priority": "HIGH",
    "status": "TODO",
    "progress": 0,
    "userId": null,
    "comments": []
  }
]

```

## 5. Assign task to user

- Description: Assign a task to a user by ids
- Endpoint: `/tasks/{taskid}/{userId}`
- Method: `PATCH`
- Request: `/tasks/1/2`
- Response:

```json
{
  "id": 1,
  "title": "Task 1",
  "description": "description",
  "dueDate": "10.02.2023",
  "priority": "HIGH",
  "status": "DONE",
  "progress": 100,
  "userId": 2,
  "comments": []
}
```

## 6. Update progress

- Description: Update the progress of a given task
- Endpoint: `/tasks/updateProgress`
- Method: `PUT`
- Request body:

```json
{
  "taskId": 1,
  "progress": 10
}
```

## 7. Update to done

- Description: Update the status of an task to done by id
- Endpoint: `/tasks/updateToDone/{taskId}`
- Method: `PUT`
- Request: `/tasks/updateToDone/1`

## 8. Get competition summary

- Description: Retrieves the competition of a task
- Endpoint: `/tasks/getCompletionSummary`
- Query Parameters: `taskId`
- Method: `GET`
- Request: `/tasks/getCompletionSummary?taskId=1`

## 9. Get progress summary

- Description: Retrieves the progress of a task
- Endpoint: `/tasks/getProgressSummary`
- Query Parameters: `taskId`
- Method: `GET`
- Request: `/tasks/getProgressSummary?taskId=1`

# III. Comments

## 1. Add comment

- Description: Add a comment to a task
- Endpoint: `/comments`
- Method: `POST`
- Request body:

```json
{
  "message": "first comment",
  "userId": 8,
  "taskId": 5
}
```

## 2. Get all comments

- Description: Retrieves the list of all the comments
- Endpoint: `/comments`
- Method: `GET`
- Request: `/comments`
- Response:

```json
[
  {
    "id": 4,
    "message": "test1",
    "createdAt": "2023-11-06T23:18:44.602324",
    "userId": 2,
    "taskId": 1
  },
  {
    "id": 5,
    "message": "test2",
    "createdAt": "2023-11-06T23:18:45.649786",
    "userId": 2,
    "taskId": 1
  }
]
```
