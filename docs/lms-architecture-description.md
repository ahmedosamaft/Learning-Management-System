# Architecture Description of Learning Management System (LMS)

## 1. Introduction

### 1.1 Identifying Information
- Architecture Name: Monolithic Architecture
- System of Interest: Learning Management System for Course Management and Assessment

### 1.2 Supplementary Information
- Version: 1.0
- Date: November 2024
- Status: Initial Draft
- Architecture Style: Layered Architecture

### 1.3 Other Information

#### 1.3.1 Overview
The Learning Management System (LMS) is designed as a monolithic web application that provides functionalities for course management, user management, and assessment handling. The primary stakeholders interact with the system as follows:
- Administrators: Manage system settings, users, and overall functionality.
- Instructors: Create and manage courses, assign tasks, and assess student progress.
- Students: Enroll in courses, submit assignments, and view progress reports.

Key architectural characteristics:
- Centralized deployment and management: All components are deployed together, simplifying management.
- Single codebase: Ensures consistency and ease of maintenance.
- Shared database: A unified repository for all system data.
- Integrated components: Seamless interaction between modules
- Synchronized data processing: Ensures real-time data updates.

#### 1.3.2 Architecture Evaluations
The monolithic architecture was evaluated for the following quality attributes:
- Scalability: Limited to vertical scaling; suitable for small to medium-scale use cases.
- Maintainability: Single codebase reduces overhead for smaller teams but may challenge larger systems as they grow.
- Security: Centralized control of authentication and authorization simplifies security implementation.
- Performance: Efficient for integrated workflows due to the absence of inter-service communication
- Reliability: Simplified testing and debugging improve reliability for smaller systems.
- Modifiability: Changes in one part of the system require testing the entire system, a potential trade-off.

#### 1.3.3 Rationale for Key Decisions
The decision to adopt a monolithic architecture is supported by the following considerations:
- Simplified Development and Deployment: Ideal for early-stage or small-scale applications where fast iteration is crucial.
- Ease of Debugging and Testing: A single codebase facilitates debugging and end-to-end testing.
- Reduced Complexity in Team Coordination: Suitable for teams with limited resources or experience in distributed systems.
- Transaction Management: Monolithic designs simplify database transaction handling, avoiding the complexity of distributed systems.
- Performance Suitability: Effective for tightly integrated workflows common in small to medium-scale systems

## 2. Stakeholders and Concerns

### 2.1 Stakeholders
1. System Users
   - Administrators
   - Instructors
   - Students

2. Development Team
   - Software Developers
   - Software Architects

### 2.2 Concerns
#### Purposes of the System-of-Interest
The primary purpose of the Learning Management System (LMS) is to provide a centralized platform for educational institutions to manage courses, students, instructors, and learning materials. It aims to streamline administrative tasks, improve learning outcomes, and offer a scalable, secure, and user-friendly experience for all stakeholders, including students, instructors, and administrators.

#### Suitability of the Architecture
Using a *monolithic* architecture, the system provides the following benefits:

- **Tight integration of features**: All functional modules (e.g., authentication, course management, assessments) are tightly coupled, ensuring seamless communication and consistent performance.

- **Ease of deployment**: The entire system is deployed as a single unit, reducing complexity in the deployment process.

- **Simplified development**: The monolithic design minimizes the overhead of managing inter-service communication, making it suitable for small to medium-sized teams during initial development.
While suitable for the project's scale and initial purpose, potential limitations in scalability and flexibility for future expansions must be addressed.

#### Feasibility of Construction and Deployment
- **Technology stack**: The choice of Java as the primary language ensures high performance, robust security, and extensive community support.

- **Development feasibility**: Java Spring Boot framework simplifies implementing features such as authentication, course management, and notifications.

- **Deployment feasibility**: Deployment in a monolithic architecture is straightforward, requiring only one runtime environment or container, which can simplify DevOps processes.

#### Risks and Impacts
- **Scalability challenges**: A monolithic architecture might face bottlenecks as user demands increase.

- **Single point of failure**: Issues in one module could potentially affect the entire system.

- **Stakeholder impacts**: Stakeholders benefit from rapid development and deployment but may face delays or downtime during system updates, given the monolithic design.

- **Long-term costs**: As the system evolves, maintaining a monolithic codebase might become more challenging, increasing technical debt.

#### Maintenance and Evolution
- **Maintainability**: The use of well-structured code practices, clear module boundaries, and robust testing strategies ensures maintainability. However, significant refactoring might be required for future scalability.

- **Evolution**: The architecture can evolve by:
Refactoring individual modules into services as needed (*eventual migration to microservices*, if required).
Implementing feature toggles to enable iterative deployment of new features.
Enhancing modularization within the monolithic codebase to simplify updates and feature additions.

#### Summary of Concerns
1. **Functional Concerns**
   - User authentication and authorization
   - Profile Management
   - Course management
   - Attendance Management
   - Assessment & Grading management
   - Student Progress Tracking
   - Generating Reports on student performance (Performance Analytics)
   - Media management
   - Notification system

2. **Quality Attribute Concerns**
   - **Security**: Robust authentication and encryption mechanisms protect sensitive data.

   - **Performance**: The system handles concurrent users through optimized database queries and efficient threading.

   - **Reliability**: Data integrity is ensured through ACID-compliant database transactions.

   - **Availability**: Use of robust runtime environments and regular backups to maximize uptime.

   - **Scalability**: Initial focus on horizontal scaling of the monolith; modularization can ease future transitions.

   - **Maintainability**: Clean coding practices, adherence to design patterns, and a clear separation of concerns reduce maintenance overhead.

### 2.3 Concern–Stakeholder Traceability

This section associates the identified concerns from 2.2 with the stakeholders from 2.1 who have those concerns.

| **Concern**                                   | **Stakeholders**                                                                                            |
|-----------------------------------------------|-------------------------------------------------------------------------------------------------------------|
| **Purposes of the System-of-Interest**         | System Users (Administrators, Instructors, Students), Development Team (Software Developers, Architects)     |
| **Suitability of the Architecture**           | Development Team (Software Architects, Software Developers)                                                 |
| **Feasibility of Construction and Deployment**| Development Team (Software Developers, Software Architects)                                                 |
| **Risks and Impacts**                         | System Users (Administrators, Instructors, Students), Development Team (Software Architects)                |
| **Maintenance and Evolution**                 | Development Team (Software Developers, Software Architects)                                                 |
| **Functional Concerns**                       | System Users (Administrators, Instructors, Students), Development Team (Software Developers)                |
| **Quality Attribute Concerns**                | System Users (Administrators, Instructors, Students), Development Team (Software Architects, Software Developers) |

#### Example showing association of stakeholders to concerns in an AD
-
   | Concern / Stakeholder | Administrators | Instructors | Students | Developers |
   |----------------------|----------------|-------------|----------|------------|
   | Authentication       | X              | X           | X        | X          |
   | Course Management    | X              | X           | X        | X          |
   | Assessment          | X              | X           | X        | X          |
   | Performance         | X              | X           | X        | X          |
   | Security            | X              | X           | X        | X          |
   | Maintenance         | X              |             |          | X          |


#### Additional Details
1. **System Users:**
   - **Administrators** are concerned with functional aspects like user authentication, course management, attendance tracking, and system reliability.
   - **Instructors** focus on ease of use for course management, grading, and performance tracking.
   - **Students** prioritize accessibility, performance, and availability for their learning materials and progress tracking.

2. **Development Team:**
   - **Software Developers** are responsible for implementing features while ensuring maintainability and performance.
   - **Software Architects** focus on architecture suitability, scalability, and risk mitigation for long-term evolution.


## 3. Viewpoints

### 3.1 Logical Viewpoint
#### Overview
The logical viewpoint describes the functional organization of the LMS, focusing on the system’s components and their responsibilities. It illustrates how the main modules interact to deliver core functionalities such as user management, course management, assessments, and notifications.

#### Concerns Addressed
- Functional decomposition of the system into modules.
- Interactions between components.
- Role-based access to functionalities.
- Data flow and process synchronization.

#### Typical Stakeholders
- **Developers**: Understand functional dependencies and system organization.
- **Architects**: Ensure modular and maintainable design.
- **System Administrators**: Manage configurations and dependencies.

#### Model Kinds
- **Use Case Diagram**: Depicts system functionalities for each user role (Admin, Instructor, Student).
- **Sequence Diagram**: Illustrates workflows such as student enrollment, instructor grading, and notification generation.

#### Operations on Views
- **Construction Methods**:  
  Use process guidance to break the LMS into modules such as User Management, Course Management, Assessment, and Notification. Apply UML-based patterns for creating use case and sequence diagrams.
- **Interpretation Methods**:  
  Provide stakeholders with a guide to trace workflows (e.g., student enrollment) across modules and interpret interactions in sequence diagrams.
- **Analysis Methods**:  
  Evaluate the logical consistency of functional decomposition and ensure workflows do not introduce redundant or conflicting operations.
- **Implementation Methods**:  
  Use the logical view as a blueprint for implementing system components in Spring Boot.

#### Correspondence Rules
- Each functional component in the logical view must map directly to a corresponding module in the implementation view.
- Interactions between components in the sequence diagrams must match the APIs defined in the implementation.

---

### 3.2 Data Viewpoint
#### Overview
The data viewpoint focuses on how data is structured, stored, and exchanged within the system. It defines the relationships between entities such as users, courses, and assessments, ensuring data consistency and integrity.

#### Concerns Addressed
- Structure of data and database schemas.
- Relationships between entities (e.g., users, courses, assessments).
- Data flow between modules for various operations like enrollment, grading, and notifications.

#### Typical Stakeholders
- **Database Administrators**: Optimize data schemas and manage integrity.
- **Developers**: Interact with data through APIs and ensure proper CRUD operations.
- **System Architects**: Verify that data structures meet functional and performance requirements.

#### Model Kinds
- **Entity-Relationship Diagram (ERD)**: Illustrates data entities such as users, courses, assessments, and their relationships.
- **Data Flow Diagram (DFD)**: Shows the flow of data between system components such as enrollment processes and grading.

#### Operations on Views
- **Construction Methods**:  
  Use entity-relationship modeling techniques to create ERDs for key entities (Users, Courses, Assessments, Notifications). Develop data flow diagrams to map how data is exchanged between modules.
- **Interpretation Methods**:  
  Provide a walkthrough of how each entity (e.g., Users) interacts with others and how data flows between operations (e.g., student enrollment, grading).
- **Analysis Methods**:  
  Validate referential integrity between related entities (e.g., Users ↔ Courses) and ensure the data flow supports operational needs.
- **Implementation Methods**:  
  Use the data view as the basis for designing database schemas and defining APIs for CRUD operations.

#### Correspondence Rules
- Each entity in the ERD must have a corresponding table in the database schema.
- Data flows defined in the DFD must align with the methods and data operations implemented in the backend.

---

### 3.3 Deployment Viewpoint
#### Overview
The deployment viewpoint describes the physical deployment of the LMS system, including servers, databases, and network infrastructure.

#### Concerns Addressed
- Physical placement of system components (frontend, backend, database).
- Scalability and availability considerations.
- Security of network communication and data storage.

#### Typical Stakeholders
- **System Administrators**: Responsible for managing server infrastructure and deployments.
- **Network Engineers**: Ensure secure and efficient communication between system components.
- **Users**: Indirect stakeholders relying on a reliable and secure system.

#### Model Kinds
- **Deployment Diagram**: Depicts how the LMS components are deployed across servers and databases.
- **Network Topology Diagram**: Visualizes the network setup and communication flow between components.

#### Operations on Views
- **Construction Methods**:  
  Use deployment modeling tools to define the allocation of components (e.g., frontend, backend, database) to servers. Specify communication paths in network topology diagrams.
- **Interpretation Methods**:  
  Help system administrators and network engineers understand how components communicate and interact physically.
- **Analysis Methods**:  
  Evaluate the deployment setup for potential bottlenecks and security vulnerabilities.
- **Implementation Methods**:  
  Use the deployment view to guide server configurations, load balancing, and setting up secure communication channels.

#### Correspondence Rules
- Each logical module must be allocated to a specific deployment node in the deployment diagram.
- Communication paths in the network topology must reflect the interactions defined in the sequence diagrams.

---

## 4. Views+

Much of the material in an Architecture Description (AD) is presented through its architecture views. Each view adheres to the conventions of its governing viewpoint. A view is composed of architecture models that address the concerns framed by the governing viewpoint.

---

### 4.1 View: Logical View
#### Identifying Information
- **Name**: Logical View
- **Purpose**: To define the functional decomposition of the LMS and describe how the system’s components interact to fulfill requirements.
- **Scope**: Covers the major modules of the system, such as User Management, Course Management, Assessment, and Notification.
- **Viewpoint**: Logical Viewpoint (Section 3.1)

---

#### 4.1.1 Models+
- **Use Case Diagram**
    - **Description**: Depicts the interactions between the primary actors (Admins, Instructors, Students) and the system.
    - **Governing Model Kind**: Use Case Model (Section 3.1).
    - **Version**: 1.0
    - **Details**:
        - Admins can create users and manage courses.
        - Instructors can create courses, grade assessments, and track student progress.
        - Students can enroll in courses, submit assignments, and view notifications.

- **Sequence Diagram**
    - **Description**: Illustrates workflows such as student enrollment, assignment grading, and notification delivery.
    - **Governing Model Kind**: Sequence Model (Section 3.1).
    - **Version**: 1.0
    - **Details**:
        - Students enroll in courses → Enrollment Service updates database → Notification is generated for the student.
        - Instructors grade assignments → Grading Service updates database → Notification is sent to the student.

---

#### 4.1.2 Known Issues with View
- No inconsistencies identified in version 1.0.
- Future iterations may need to refine interactions if new modules are added.

---

### 4.2 View: Data View
#### Identifying Information
- **Name**: Data View
- **Purpose**: To define the structure of the data within the LMS, including relationships between key entities.
- **Scope**: Includes Users, Courses, Assessments, and Notifications.
- **Viewpoint**: Data Viewpoint (Section 3.2)

---

#### 4.2.1 Models+
- **Entity-Relationship Diagram (ERD)**
    - **Description**: Illustrates the relationships between key entities in the LMS.
    - **Governing Model Kind**: ERD Model (Section 3.2).
    - **Version**: 1.0
    - **Details**:
        - Users can have multiple roles (e.g., Admin, Instructor, Student).
        - Courses are managed by Instructors and enrolled in by Students.
        - Assessments are tied to courses and can include quizzes and assignments.

- **Data Flow Diagram (DFD)**
    - **Description**: Visualizes the flow of data between modules such as Enrollment, Grading, and Notification.
    - **Governing Model Kind**: DFD Model (Section 3.2).
    - **Version**: 1.0
    - **Details**:
        - Enrollment: Data flows from the Enrollment Service to update the database and generate notifications.
        - Grading: Data flows from the Grading Service to update assignment scores and notify students.

---

#### 4.2.2 Known Issues with View
- None identified for version 1.0.
- Future improvements may include enhanced normalization of database schemas.

---

### 4.3 View: Deployment View
#### Identifying Information
- **Name**: Deployment View
- **Purpose**: To describe the physical deployment of LMS components on servers and their communication paths.
- **Scope**: Includes frontend, backend, and database deployment.
- **Viewpoint**: Deployment Viewpoint (Section 3.3)

---

#### 4.3.1 Models+
- **Deployment Diagram**
    - **Description**: Visualizes the allocation of LMS components to physical nodes.
    - **Governing Model Kind**: Deployment Model (Section 3.3).
    - **Version**: 1.0
    - **Details**:
        - Frontend: Deployed on a web server.
        - Backend: Deployed as a Spring Boot service on an application server.
        - Database: Hosted on a dedicated database server (MySQL/PostgreSQL).

- **Network Topology Diagram**
    - **Description**: Depicts communication paths between system components and external users.
    - **Governing Model Kind**: Topology Model (Section 3.3).
    - **Version**: 1.0
    - **Details**:
        - HTTPS is used for secure communication between frontend and backend.
        - Backend communicates with the database over a private network.

---

#### 4.3.2 Known Issues with View
- None identified for version 1.0.
- Scalability strategies may require revisiting deployment configurations.

---

## 5. Consistency and Correspondences

### 5.1 Known Inconsistencies
- **Current Status**: No known inconsistencies have been identified at this stage of the architecture.
- **Future Management**: 
  - Any detected inconsistencies will be documented, including:
    - The affected components or models.
    - The reasons for the inconsistency (e.g., limited time, evolving requirements).
    - Proposed resolutions and their timelines.

---

### 5.2 Correspondences
Correspondences ensure consistency between the various elements of the AD and their implementation. Below are the key correspondences for the LMS:

- **Logical Components ↔ Spring Boot Components**:
  - Each logical feature (e.g., authentication, course management) is implemented using specific Spring Boot components (e.g., controllers, services, repositories).
  - Example: `AuthenticationService` ↔ `AuthenticationController`.

- **Database Entities ↔ Domain Models**:
  - Database tables are directly mapped to domain model classes for data representation.
  - Example: `users` table ↔ `User` entity.

- **REST Endpoints ↔ Service Operations**:
  - Each REST API endpoint corresponds to a defined service operation.
  - Example: `GET /api/courses` ↔ `CourseService.getAllCourses()`.

- **Stakeholder Needs ↔ Functional Modules**:
  - Stakeholder concerns are addressed through corresponding functional modules.
  - Example: "Course Management" (Instructor need) ↔ `CourseModule`.

- **Views ↔ Code Implementation**:
  - The functional and logical views of the system map directly to modules in the codebase.
  - Example: "Assessment View" ↔ `AssessmentService` + `AssessmentController`.

---

### 5.3 Correspondence Rules
- **Layered Rule**:
  - Each layer (Presentation, Business Logic, Data) must only interact with its adjacent layer.
  - Example: Controllers can call services, but not directly access repositories.

- **Service Consistency Rule**:
  - Every service method should align with a specific business requirement and be documented.
  - Example: `registerStudent()` ↔ Registration requirements.

- **Database-Model Rule**:
  - Every database table must map to a domain model class.
  - Example: `grades` table ↔ `Grade` class.

- **Traceability Rule**:
  - All implemented features must trace back to documented stakeholder needs and concerns.
  - Example: "Progress Tracking" (Student need) ↔ `ProgressService`.

- **Deployment Rule**:
  - Deployment artifacts (e.g., JAR file) must include all modules defined in the architecture.

**Violation Handling**:
- Any violation of correspondence rules will be documented with:
  - A description of the inconsistency.
  - The affected AD elements.
  - Proposed mitigation strategies and timelines.


# A. Architecture Decisions and Rationale


## A.1 Decisions

### Key Architectural Decisions for LMS
The following decisions are considered key to the architecture of the LMS:

- **Choice of Monolithic Architecture**:
  - A monolithic architecture was chosen for simplicity in development and deployment.
  
- **Technology Stack (Java with Spring Boot)**:
  - The decision to use Java and Spring Boot ensures a solid, widely-used framework with a large development community.

- **Relational Database (PostgreSQL)**:
  - A relational database like PostgreSQL was selected to manage the structured data for courses, assessments, and user profiles.