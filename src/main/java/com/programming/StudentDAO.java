package com.programming;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // ============================================================
    // READ - Getting all students
    // ============================================================

    public List<Student> getAllStudents() {

        List<Student> students = new ArrayList<>();

        String sql = "SELECT * FROM students";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Student student = new Student(
                        resultSet.getInt("student_id"),
                        resultSet.getString("student_name"),
                        resultSet.getString("phone_no"),
                        resultSet.getString("gender"),
                        resultSet.getInt("study_hours"),
                        resultSet.getString("part_time_job"),
                        resultSet.getInt("math"),
                        resultSet.getInt("physics"),
                        resultSet.getInt("chemistry"),
                        resultSet.getString("grade"),
                        resultSet.getString("comment"),
                        resultSet.getString("course_recommendation"),
                        resultSet.getString("course_code"),
                        resultSet.getString("list_of_courses"),
                        resultSet.getInt("rating_of_courses")
                );

                students.add(student);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving students: " + e.getMessage());
        }

        return students;
    }


    // ============================================================
    // READ - For Extracting a specific student by its ID
    // ============================================================

    public Student getStudentById(int studentId) {

        String sql = "SELECT * FROM students WHERE student_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    return new Student(
                            resultSet.getInt("student_id"),
                            resultSet.getString("student_name"),
                            resultSet.getString("phone_no"),
                            resultSet.getString("gender"),
                            resultSet.getInt("study_hours"),
                            resultSet.getString("part_time_job"),
                            resultSet.getInt("math"),
                            resultSet.getInt("physics"),
                            resultSet.getInt("chemistry"),
                            resultSet.getString("grade"),
                            resultSet.getString("comment"),
                            resultSet.getString("course_recommendation"),
                            resultSet.getString("course_code"),
                            resultSet.getString("list_of_courses"),
                            resultSet.getInt("rating_of_courses")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error finding student: " + e.getMessage());
        }

        return null;
    }


    // ============================================================
    // CREATE - Add a new student
    // ============================================================

    public int addStudent(
            String studentName,
            String phoneNo,
            String gender,
            int studyHours,
            String partTimeJob,
            int math,
            int physics,
            int chemistry,
            String grade,
            String comment) {

        String sql = """
            INSERT INTO students
            (
                student_name,
                phone_no,
                gender,
                study_hours,
                part_time_job,
                math,
                physics,
                chemistry,
                grade,
                comment
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             java.sql.Statement.RETURN_GENERATED_KEYS
                     )) {

            statement.setString(1, studentName);
            statement.setString(2, phoneNo);
            statement.setString(3, gender);
            statement.setInt(4, studyHours);
            statement.setString(5, partTimeJob);
            statement.setInt(6, math);
            statement.setInt(7, physics);
            statement.setInt(8, chemistry);
            statement.setString(9, grade);
            statement.setString(10, comment);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted == 0) {
                return -1;
            }

            // Getting the ID generated by MySQL
            try (ResultSet generatedKeys =
                         statement.getGeneratedKeys()) {

                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error adding student: " +
                            e.getMessage()
            );
        }

        return -1;
    }


    // ============================================================
    // UPDATE - For Updating an existing student
    // ============================================================

    public boolean updateStudent(
            int studentId,
            String studentName,
            String phoneNo,
            String gender,
            int studyHours,
            String partTimeJob,
            int math,
            int physics,
            int chemistry,
            String grade,
            String comment,
            String courseRecommendation,
            String courseCode,
            String listOfCourses,
            int ratingOfCourses) {

        String sql = """
                UPDATE students
                SET
                    student_name = ?,
                    phone_no = ?,
                    gender = ?,
                    study_hours = ?,
                    part_time_job = ?,
                    math = ?,
                    physics = ?,
                    chemistry = ?,
                    grade = ?,
                    comment = ?,
                    course_recommendation = ?,
                    course_code = ?,
                    list_of_courses = ?,
                    rating_of_courses = ?
                WHERE student_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, studentName);
            statement.setString(2, phoneNo);
            statement.setString(3, gender);
            statement.setInt(4, studyHours);
            statement.setString(5, partTimeJob);
            statement.setInt(6, math);
            statement.setInt(7, physics);
            statement.setInt(8, chemistry);
            statement.setString(9, grade);
            statement.setString(10, comment);
            statement.setString(11, courseRecommendation);
            statement.setString(12, courseCode);
            statement.setString(13, listOfCourses);
            statement.setInt(14, ratingOfCourses);
            statement.setInt(15, studentId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error updating student: " + e.getMessage());
            return false;
        }
    }


    // ============================================================
    // DELETE - Deleting a student
    // ============================================================

    public boolean deleteStudent(int studentId) {

        String sql = "DELETE FROM students WHERE student_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting student: " + e.getMessage());
            return false;
        }
    }
}