package com.programming;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    public List<Course> getAllCourses() {

        List<Course> courses = new ArrayList<>();

        String sql = "SELECT * FROM courses";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

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