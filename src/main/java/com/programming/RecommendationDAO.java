package com.programming;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecommendationDAO {

    public List<Course> getRecommendedCourses(int studentId) {

        List<Course> courses = new ArrayList<>();

        String sql = """
                SELECT c.course_code, c.course_name
                FROM recommendations r
                JOIN courses c
                    ON r.course_code = c.course_code
                WHERE r.student_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Course course = new Course(
                        resultSet.getString("course_code"),
                        resultSet.getString("course_name")
                );

                courses.add(course);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }
}