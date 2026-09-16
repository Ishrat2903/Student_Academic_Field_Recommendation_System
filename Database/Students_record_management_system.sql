-- Creating Schema
CREATE DATABASE student_management;

USE student_management;

-- Creating Table for populating the dataset

CREATE TABLE students (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    student_name VARCHAR(100),
    phone_no VARCHAR(20),
    gender VARCHAR(20),
    study_hours INT,
    part_time_job VARCHAR(10),
    math INT,
    physics INT,
    chemistry INT,
    grade VARCHAR(5),
    comment VARCHAR(255),
    course_recommendation VARCHAR(255),
    course_code VARCHAR(50),
    list_of_courses VARCHAR(255),
    rating_of_courses DECIMAL(3,1)
);

-- Verifying the Students table after creation

DESCRIBE students;
SELECT * FROM Students;

-- /importing the data into the table/

LOAD DATA LOCAL INFILE 'Dataset/student_dataset_v1.csv'  -- Please enter the correct file path to import the dataset.
INTO TABLE Students
FIELDS TERMINATED BY ','  -- CSV delimiter
ENCLOSED BY '"'           -- Enclosing character for text (optional)
LINES TERMINATED BY '\n'  -- New line to mark each record
IGNORE 1 LINES            -- Ignore the first row (header)
(student_name, phone_no, gender, study_hours, part_time_job, math, physics, chemistry, grade, comment, course_recommendation, course_code, list_of_courses, rating_of_courses);

set sql_safe_updates = 0;

Set global local_infile = 1;

Show variables like 'local_infile';

SELECT COUNT(*) AS total_students
FROM students;

SELECT * FROM Students;

-- Creating a separate table for courses and their course codes

CREATE TABLE courses (
    course_code VARCHAR(50) PRIMARY KEY,
    course_name VARCHAR(255) NOT NULL
);

INSERT INTO courses (course_code, course_name)
SELECT DISTINCT course_code, list_of_courses
FROM students
WHERE course_code IS NOT NULL
AND list_of_courses IS NOT NULL;
  
  
SELECT * FROM courses;

-- Creating a separate table for recommendations

CREATE TABLE recommendations (
    recommendation_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    course_code VARCHAR(50) NOT NULL,

    FOREIGN KEY (student_id)
	REFERENCES students(student_id),

    FOREIGN KEY (course_code)
	REFERENCES courses(course_code)
);

-- Verifying the normalized recommendation records
SELECT * FROM recommendations;


-- Transforming course recommendations into atomic values
-- to satisfy the First Normal Form (1NF)

INSERT INTO recommendations (student_id, course_code)

SELECT student_id, TRIM(SUBSTRING_INDEX(course_recommendation, ',', 1)) AS course_code
FROM students
WHERE course_recommendation IS NOT NULL
AND course_recommendation <> 'Better luck next time'

UNION ALL

SELECT student_id, TRIM(SUBSTRING_INDEX(SUBSTRING_INDEX(course_recommendation, ',', 2), ',', -1)) AS course_code
FROM students
WHERE course_recommendation IS NOT NULL
AND course_recommendation <> 'Better luck next time'
AND course_recommendation LIKE '%,%'

UNION ALL

SELECT student_id, TRIM(SUBSTRING_INDEX(course_recommendation, ',', -1)) AS course_code
FROM students
WHERE course_recommendation IS NOT NULL
AND course_recommendation <> 'Better luck next time'
AND course_recommendation LIKE '%,%,%';

