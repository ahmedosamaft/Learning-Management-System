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
The Learning Management System (LMS) is designed as a monolithic web application that provides comprehensive course management, assessment, and user management capabilities. The system serves three main user types: administrators, instructors, and students, each with specific roles and permissions.

Key architectural characteristics:
- Centralized deployment and management
- Single codebase
- Shared database
- Integrated components
- Synchronized data processing

#### 1.3.2 Architecture Evaluations
The architecture has been evaluated against the following quality attributes:
- Scalability
- Maintainability
- Security
- Performance
- Reliability
- Modifiability

#### 1.3.3 Rationale for Key Decisions
The monolithic architecture was chosen for the following reasons:
1. Simplified development and deployment process
2. Easier debugging and testing
3. Reduced complexity in team coordination
4. Straightforward transaction management
5. Better performance for small to medium-scale applications

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

### 3.1 Conceptual Viewpoint
#### Overview
The Conceptual viewpoint describes the functional organization of the LMS, focusing on the system’s components and their responsibilities. It illustrates how the main modules interact to deliver core functionalities such as user management, course management, assessments, and notifications.

#### Concerns Addressed
- Functional decomposition of the system into modules.
- Interactions between components.
- Role-based access to functionalities.
- Data flow and process synchronization.

#### Typical Stakeholders
- **Developers**: Understand functional dependencies and system organization.
- **Architects**: Ensure modular and maintainable design.
- **System Administrators**: Manage configurations and dependencies.

---

### 3.2 Execution Viewpoint
#### Overview
The deployment viewpoint describes the physical deployment of the LMS system, including servers, databases, and network infrastructure.

#### Concerns Addressed
- Physical placement of system components (frontend, backend, database).
- Scalability and availability considerations.
- Security of network communication and data storage.

#### Typical Stakeholders
- **Developers**: Understand functional dependencies and system organization.
- **Architects**: Ensure modular and maintainable design.
- **System Administrators**: Responsible for managing server infrastructure and deployments.
- **Users**: Indirect stakeholders relying on a reliable and secure system.

---

### 3.3 Data Viewpoint
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

---

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
