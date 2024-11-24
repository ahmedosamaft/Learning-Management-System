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

### 2.3 Concern-Stakeholder Traceability

| Concern / Stakeholder | Administrators | Instructors | Students | Developers |
|----------------------|----------------|-------------|----------|------------|
| Authentication       | X              | X           | X        | X          |
| Course Management    | X              | X           | X        | X          |
| Assessment          | X              | X           | X        | X          |
| Performance         | X              | X           | X        | X          |
| Security            | X              | X           | X        | X          |
| Maintenance         | X              |             |          | X          |

## 3. Viewpoints

### 3.1 Logical Viewpoint

#### Overview
The logical viewpoint describes the functional components of the system and their relationships.

#### Concerns Addressed
- System structure and organization
- Component responsibilities
- Data flow between components
- System boundaries

#### Typical Stakeholders
- Developers
- Architects
- System Administrators

### 3.2 Implementation Viewpoint

#### Overview
The implementation viewpoint describes how the system is implemented using Spring Boot and related technologies.

#### Concerns Addressed
- Code organization
- Framework usage
- Database implementation
- Security implementation

#### Typical Stakeholders
- Developers
- QA Engineers
- System Operators

## 4. Views

### 4.1 Logical View

#### Components
1. User Management Module
   - Authentication Controller
   - User Service
   - Profile Management
   - Role Management

2. Course Management Module
   - Course Controller
   - Enrollment Service
   - Content Management
   - Attendance Service

3. Assessment Module
   - Quiz Controller
   - Assignment Controller
   - Grading Service
   - Progress Tracking

4. Notification Module
   - Notification Controller
   - Email Service
   - System Notification Service

### 4.2 Implementation View

#### Technology Stack
- Backend: Spring Boot
- Database: MySQL
- Security: Spring Security
- Testing: JUnit
- Version Control: Git

#### Layer Organization
1. Presentation Layer
   - REST Controllers
   - Request/Response DTOs
   - Validation

2. Business Layer
   - Services
   - Business Logic
   - Domain Models

3. Data Access Layer
   - Repositories
   - Entity Models
   - Database Connections

## 5. Consistency and Correspondences

### 5.1 Known Inconsistencies
- None identified at this stage

### 5.2 Correspondences
- Each logical component maps to specific Spring Boot components
- Database entities correspond to domain models
- REST endpoints correspond to service operations

## Appendix A: Architecture Decisions and Rationale

### A.1 Key Decisions

1. Monolithic Architecture
   - Decision: Use a monolithic architecture instead of microservices
   - Rationale: Simpler development, deployment, and maintenance for the initial scope
   - Impact: Lower initial complexity, faster development

2. Spring Boot Framework
   - Decision: Use Spring Boot for backend development
   - Rationale: Robust ecosystem, built-in security, ease of development
   - Impact: Standardized development practices, better maintainability

3. MySQL Database
   - Decision: Use MySQL as the primary database
   - Rationale: ACID compliance, strong consistency, familiar to team
   - Impact: Reliable data storage, easier maintenance

4. Spring Security
   - Decision: Implement authentication using Spring Security
   - Rationale: Built-in security features, integration with Spring Boot
   - Impact: Robust security implementation, standard security practices
