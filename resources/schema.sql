PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS instructors (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS courses (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL UNIQUE,
    price REAL NOT NULL CHECK(price > 0),
    type TEXT NOT NULL,
    instructor_id INTEGER,
    video_hours INTEGER,
    live_sessions INTEGER,
    FOREIGN KEY (instructor_id) REFERENCES instructors(id)
);

CREATE TABLE IF NOT EXISTS enrollments (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    student_id INTEGER NOT NULL,
    course_id INTEGER NOT NULL,
    UNIQUE(student_id, course_id),
    FOREIGN KEY (course_id) REFERENCES courses(id)
);

