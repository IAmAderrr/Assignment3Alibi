# Assignment 4 — SOLID Architecture & Advanced OOP 

## Overview

This project is a Java Course Management System developed using JDBC and SQLite.  
It demonstrates advanced object-oriented principles, SOLID principles, database interaction, exception handling, and repository-based design.

The system supports different course types (`VideoCourse`, `LiveCourse`), instructors, and student enrollments, with persistent storage handled via SQLite.

---

## Project Structure
```bash
Assignment4/
├── src/
│   ├── model/
│   │   ├── Course.java
│   │   ├── VideoCourse.java
│   │   ├── LiveCourse.java
│   │   ├── Instructor.java
│   │   └── Enrollment.java
│   ├── interfaces/
│   │   ├── Payable.java
│   │   └── Validatable.java
│   ├── repository/
│   │   ├── CourseRepository.java
│   │   ├── InstructorRepository.java
│   │   ├── EnrollmentRepository.java
│   │   └── interfaces/
│   │       ├── CourseRepositoryInterface.java
│   │       ├── InstructorRepositoryInterface.java
│   │       └── EnrollmentRepositoryInterface.java
│   ├── service/
│   │   ├── CourseService.java
│   │   ├── InstructorService.java
│   │   ├── EnrollmentService.java
│   │   └── interfaces/
│   │       ├── CourseServiceInterface.java
│   │       ├── InstructorServiceInterface.java
│   │       └── EnrollmentServiceInterface.java
│   ├── exception/
│   │   ├── DatabaseOperationException.java
│   │   ├── DuplicateResourceException.java
│   │   ├── InvalidInputException.java
│   │   └── ResourceNotFoundException.java
│   ├── utils/
│   │   ├── DatabaseConnection.java
│   │   └── SchemaRunner.java
│   └── Main.java
├── resources/
│   └── schema.sql
├── lib/
│   └── sqlite-jdbc-3.51.1.0.jar
├── docs/
│   └── uml.png
└── README.md
```
---

## Database

### Database Type

- SQLite  
- File name: `courses.db`

### Connection

The database connection is managed via a dedicated utility class:

jdbc:sqlite:courses.db


The database file is created automatically in the project root when the application is first run.

---

## Schema Initialization

The database schema is defined in:

resources/schema.sql


It includes the following tables:

- courses
- instructors
- enrollments

The schema is executed automatically when the application is run using the `SchemaRunner` utility class. This ensures the required tables exist before any CRUD operations are performed.

---

## Object-Oriented Design

### Inheritance & Polymorphism

- `Course` is an abstract base class
- `VideoCourse` and `LiveCourse` extend `Course`
- Polymorphism is used to calculate final prices differently per course type

### Interfaces

- `Validatable` → enforces domain-level input validation
- `Payable` → defines payment and pricing behavior

### Composition

- A `Course` may have an associated `Instructor`
- An `Enrollment` links a student to a course

---
## SOLID Principles

- Single Responsibility Principle
  Each class has one clearly defined responsibility
- Open/Closed Principle
  New course types can be added without modifying existing logic
- Liskov Substitution Principle
  VideoCourse and LiveCourse can be used anywhere Course is expected
- Interface Segregation Principle
  Small, focused interfaces (Validatable, Payable)
- Dependency Inversion Principle
  Business logic depends on abstractions, not concrete implementations
  
---

## Exception Handling

Custom exceptions are used to clearly separate error types:

- `DuplicateResourceException`  
  → thrown when database UNIQUE constraints are violated
- `InvalidInputException`  
  → thrown for invalid domain input
- `DatabaseOperationException`  
  → wraps low-level SQL errors
- `ResourceNotFoundException`  
  → used when requested records do not exist

Duplicate detection is handled safely by inspecting SQL error messages rather than masking all SQL errors.

---

## How to Compile and Run

### Compile (from project root)
```bash
dir /s /b src*.java > sources.txt
javac -d src\out @sources.txt
```

### Run
```bash
java -cp "src\out;lib\sqlite-jdbc-3.51.1.0.jar" Main
```

---

## Sample Output

1 - Java Basics | VIDEO | final=100.0
2 - Spring Boot Live | LIVE | final=180.0


---

## Conclusion

This project demonstrates clean SOLID-compliant architecture, effective JDBC + SQLite integration, and robust exception handling, providing a maintainable and extensible foundation for a real-world course management system.


