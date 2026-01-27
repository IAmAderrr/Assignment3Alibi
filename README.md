Assignment 3 — Course Management System (JDBC + SQLite)
Overview

This project is a Java Course Management System developed using JDBC and SQLite.
It demonstrates object-oriented principles, database interaction, exception handling, and repository-based design.

The system supports different course types (VideoCourse, LiveCourse), instructors, and student enrollments, with persistent storage handled via SQLite.

Project Structure
Assignment3/
├── src/
│   ├── model/
│   │   ├── Course.java
│   │   ├── VideoCourse.java
│   │   ├── LiveCourse.java
│   │   ├── Instructor.java
│   │   └── Enrollment.java
│   ├── repository/
│   │   ├── CourseRepository.java
│   │   ├── InstructorRepository.java
│   │   └── EnrollmentRepository.java
│   ├── exception/
│   │   ├── DatabaseOperationException.java
│   │   ├── DuplicateResourceException.java
│   │   ├── InvalidInputException.java
│   │   └── ResourceNotFoundException.java
│   ├── interfaces/
│   │   ├── Validatable.java
│   │   └── Payable.java
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

Database
Database Type

SQLite

File name: courses.db

Connection

The database connection is managed via a dedicated utility class:

jdbc:sqlite:courses.db


The database file is created automatically in the project root when the application is first run.

Schema Initialization

The database schema is defined in:

resources/schema.sql


It includes the following tables:

courses

instructors

enrollments

The schema is executed automatically when the application is run using the SchemaRunner utility class. This ensures the required tables exist before any CRUD operations are performed.

Object-Oriented Design
Inheritance & Polymorphism

Course is an abstract base class

VideoCourse and LiveCourse extend Course

Polymorphism is used to calculate final prices differently per course type

Interfaces

Validatable → input validation

Payable → payment calculation

Composition

A Course may have an associated Instructor

An Enrollment links a student to a course

Exception Handling

Custom exceptions are used to clearly separate error types:

DuplicateResourceException
→ thrown when database UNIQUE constraints are violated

InvalidInputException
→ thrown for invalid domain input

DatabaseOperationException
→ wraps low-level SQL errors

ResourceNotFoundException
→ used when requested records do not exist

Duplicate detection is handled safely by inspecting SQL error messages rather than masking all SQL errors.

How to Compile and Run
Compile (from project root)
dir /s /b src\*.java > sources.txt
javac -d src\out @sources.txt

Run
java -cp "src\out;lib\sqlite-jdbc-3.51.1.0.jar" Main

Sample Output
1 - Java Basics | VIDEO | final=100.0
2 - Spring Boot Live | LIVE | final=180.0

Conclusion

This project demonstrates use of Java OOP, JDBC, and SQLite, with clean architecture and proper exception handling.
