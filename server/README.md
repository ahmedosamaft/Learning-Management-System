# Learning-Management-System

The LMS project is required for managing and organizing online courses, and assessments from the perspective of students
and instructors. It should support a range of user needs, each with specific features. The primary functionalities will
include course creation and management, user management, assessments, and performance tracking.

## Prerequisites

- **Docker**
- **Docker Compose**

## Getting Started

1. **Clone the Repository:**
    ``` bash
    git clone https://github.com/ahmedosamaft/Learning-Management-System.git
    cd Learning-Management-System
    ```
2. **Start the Services:** Use Docker Compose to start **PostgreSQL**, **pgAdmin**, and **Redis** containers.
     ``` bash
    docker compose up -d
    ```
    - The `-d` flag runs the services in detached mode (in the background).

## Accessing pgAdmin (Postgres Dashboard)

1. **Open pgAdmin:** Go to your web browser and open pgAdmin at http://localhost:5050.
2. **Log in to pgAdmin:**
    - **Email**: `admin@fcai.com`
    - **Password**: `admin`
3. **Add PostgreSQL Server in pgAdmin:**
    - After logging in, go to **Servers** > **Create** > **Server**.
    - **General Tab:**
        - **Name:** Give a name to the server, e.g., _Postgres_.
    - **Connection Tab:**
        - **Host:** `host.docker.internal`
        - **Port:** `5432`
        - **Username:** `postgres`
        - **Password:** `password`

## Useful Commands

- **Stop Services:**
  ``` bash
  docker compose down
  ```
- **View Logs:**
  ``` bash
  docker compose logs
  ```

## Configuration Summary

- **PostgreSQL:**

    - **Database Name:** lms
    - **Username:** postgres
    - **Password:** password
    - **Port:** 5432

- **pgAdmin:**

    - **URL:** http://localhost:5050
    - **Email:** `admin@fcai.com`
    - **Password:** `admin`