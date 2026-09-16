package com.programming;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatasetAnalysis {

    public static void main(String[] args) {

        try (Connection connection = DatabaseConnection.getConnection()) {

            // Find total number of students
            String query1 = "SELECT COUNT(*) AS total_students FROM students";

            PreparedStatement ps1 = connection.prepareStatement(query1);
            ResultSet rs1 = ps1.executeQuery();

            if (rs1.next()) {
                System.out.println("Total students: "
                        + rs1.getInt("total_students"));
            }


            // Find minimum and maximum marks
            String query2 = "SELECT " +
                    "MIN(math) AS min_math, MAX(math) AS max_math, " +
                    "MIN(physics) AS min_physics, MAX(physics) AS max_physics, " +
                    "MIN(chemistry) AS min_chemistry, MAX(chemistry) AS max_chemistry " +
                    "FROM students";

            PreparedStatement ps2 = connection.prepareStatement(query2);
            ResultSet rs2 = ps2.executeQuery();

            if (rs2.next()) {
                System.out.println("Minimum Math: "
                        + rs2.getInt("min_math"));

                System.out.println("Maximum Math: "
                        + rs2.getInt("max_math"));

                System.out.println("Minimum Physics: "
                        + rs2.getInt("min_physics"));

                System.out.println("Maximum Physics: "
                        + rs2.getInt("max_physics"));

                System.out.println("Minimum Chemistry: "
                        + rs2.getInt("min_chemistry"));

                System.out.println("Maximum Chemistry: "
                        + rs2.getInt("max_chemistry"));
            }


            // Count students for each recommendation
            String query3 = "SELECT course_recommendation, COUNT(*) AS total " +
                    "FROM students " +
                    "GROUP BY course_recommendation";

            PreparedStatement ps3 = connection.prepareStatement(query3);
            ResultSet rs3 = ps3.executeQuery();

            System.out.println("\nCourse Recommendations:");

            while (rs3.next()) {
                System.out.println(
                        rs3.getString("course_recommendation")
                                + " = "
                                + rs3.getInt("total"));
            }


            // Find average marks and study hours
            String query4 = "SELECT " +
                    "AVG(study_hours) AS average_study_hours, " +
                    "AVG(math) AS average_math, " +
                    "AVG(physics) AS average_physics, " +
                    "AVG(chemistry) AS average_chemistry " +
                    "FROM students";

            PreparedStatement ps4 = connection.prepareStatement(query4);
            ResultSet rs4 = ps4.executeQuery();

            if (rs4.next()) {
                System.out.println("\nAverage Study Hours: "
                        + rs4.getDouble("average_study_hours"));

                System.out.println("Average Math: "
                        + rs4.getDouble("average_math"));

                System.out.println("Average Physics: "
                        + rs4.getDouble("average_physics"));

                System.out.println("Average Chemistry: "
                        + rs4.getDouble("average_chemistry"));
            }

        } catch (SQLException e) {
            System.out.println("Error while analysing the data.");
            e.printStackTrace();
        }
    }
}