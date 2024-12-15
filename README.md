# Learning-Management-System

The LMS project is required for managing and organizing online courses, and assessments from the perspective of students
and instructors. It should support a range of user needs, each with specific features. The primary functionalities will
include course creation and management, user management, assessments, and performance tracking.
## Prerequisites

- **Docker**
- **Docker Compose**

## Installation
1. **Clone the Repository:**
    ``` bash
    git clone https://github.com/ahmedosamaft/Learning-Management-System.git
    cd Learning-Management-System
    ```
2. Run the command: 
    ``` bash
        docker build -t lms .\server
        docker build -t lms-lb .\loadbalancer
        docker compose up
    ```