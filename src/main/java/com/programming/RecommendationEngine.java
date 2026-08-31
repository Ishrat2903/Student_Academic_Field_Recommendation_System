package com.programming;

import java.util.*;

public class RecommendationEngine {

    // ============================================================
    // Generate top 3 course recommendations
    // ============================================================

    public List<String> generateRecommendations(
            int math,
            int physics,
            int chemistry) {

        // Store course + score
        Map<String, Integer> courseScores = new HashMap<>();

        // --------------------------------------------------------
        // Mathematics
        // --------------------------------------------------------

        if (math >= 70) {
            addScore(courseScores, "Computer Science", 3);
            addScore(courseScores, "Artificial Intelligence", 3);
            addScore(courseScores,
                    "Computer Science & Engineering (Robotics)", 3);
        } else if (math >= 50) {
            addScore(courseScores, "Computer Science", 1);
            addScore(courseScores,
                    "Computer Science & Engineering (Robotics)", 1);
        }

        // --------------------------------------------------------
        // Physics
        // --------------------------------------------------------

        if (physics >= 70) {
            addScore(courseScores, "Mechanical Engineering", 3);
            addScore(courseScores, "Civil Engineering", 3);
            addScore(courseScores,
                    "Computer Science & Engineering (Robotics)", 3);
        } else if (physics >= 50) {
            addScore(courseScores, "Mechanical Engineering", 1);
            addScore(courseScores,
                    "Computer Science & Engineering (Robotics)", 1);
        }

        // --------------------------------------------------------
        // Chemistry
        // --------------------------------------------------------

        if (chemistry >= 70) {
            addScore(courseScores, "Chemical Engineering", 3);
            addScore(courseScores, "Metallurgical Engineering", 3);
            addScore(courseScores, "Polymer Engineering", 3);
        } else if (chemistry >= 50) {
            addScore(courseScores, "Chemical Engineering", 1);
            addScore(courseScores, "Metallurgical Engineering", 1);
        }

        // --------------------------------------------------------
        // Sort courses by score
        // --------------------------------------------------------

        List<Map.Entry<String, Integer>> sortedCourses =
                new ArrayList<>(courseScores.entrySet());

        sortedCourses.sort(
                (a, b) -> b.getValue().compareTo(a.getValue())
        );

        // --------------------------------------------------------
        // Return top 3 courses
        // --------------------------------------------------------

        List<String> recommendations = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : sortedCourses) {

            recommendations.add(entry.getKey());

            if (recommendations.size() == 3) {
                break;
            }
        }

        return recommendations;
    }


    // ============================================================
    // Add score to a course
    // ============================================================

    private void addScore(
            Map<String, Integer> courseScores,
            String course,
            int score) {

        courseScores.put(
                course,
                courseScores.getOrDefault(course, 0) + score
        );
    }
}