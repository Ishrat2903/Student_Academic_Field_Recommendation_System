package com.programming;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        StudentDAO studentDAO = new StudentDAO();

        List<Student> students = studentDAO.getAllStudents();

        System.out.println("Total students retrieved: " + students.size());

        if (!students.isEmpty()) {

            Student firstStudent = students.get(0);

            System.out.println("First student:");
            System.out.println("ID: " + firstStudent.getStudentId());
            System.out.println("Name: " + firstStudent.getStudentName());
            System.out.println("Math: " + firstStudent.getMath());
            System.out.println("Physics: " + firstStudent.getPhysics());
            System.out.println("Chemistry: " + firstStudent.getChemistry());
            System.out.println("Grade: " + firstStudent.getGrade());
            System.out.println("Course Code: " + firstStudent.getCourseCode());


            // Get recommendations for the first student
            RecommendationDAO recommendationDAO =
                    new RecommendationDAO();

            int studentId = firstStudent.getStudentId();

            List<Course> recommendations =
                    recommendationDAO.getRecommendedCourses(studentId);

            System.out.println(
                    "Recommendations for student " + studentId + ":"
            );

            for (Course course : recommendations) {

                System.out.println(
                        course.getCourseCode()
                                + " | "
                                + course.getCourseName()
                );
            }
        }


        // Get all courses
        CourseDAO courseDAO = new CourseDAO();

        List<Course> courses = courseDAO.getAllCourses();

        System.out.println("Total courses retrieved: " + courses.size());

        for (Course course : courses) {

            System.out.println(
                    course.getCourseCode()
                            + " | "
                            + course.getCourseName()
            );
        }

        RecommendationEngine engine = new RecommendationEngine();

        List<String> testRecommendations =
                engine.generateRecommendations(80, 75, 88);

        System.out.println("\nTest Recommendations:");

        for (String recommendation : testRecommendations) {
            System.out.println(recommendation);
        }
    }
}