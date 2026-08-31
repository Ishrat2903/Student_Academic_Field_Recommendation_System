package com.programming;

public class Student {

    private int studentId;
    private String studentName;
    private String phoneNo;
    private String gender;
    private int studyHours;
    private String partTimeJob;
    private int math;
    private int physics;
    private int chemistry;
    private String grade;
    private String comment;
    private String courseRecommendation;
    private String courseCode;
    private String listOfCourses;
    private int ratingOfCourses;

    public Student(int studentId, String studentName, String phoneNo,
                   String gender, int studyHours, String partTimeJob,
                   int math, int physics, int chemistry,
                   String grade, String comment,
                   String courseRecommendation, String courseCode,
                   String listOfCourses, int ratingOfCourses) {

        this.studentId = studentId;
        this.studentName = studentName;
        this.phoneNo = phoneNo;
        this.gender = gender;
        this.studyHours = studyHours;
        this.partTimeJob = partTimeJob;
        this.math = math;
        this.physics = physics;
        this.chemistry = chemistry;
        this.grade = grade;
        this.comment = comment;
        this.courseRecommendation = courseRecommendation;
        this.courseCode = courseCode;
        this.listOfCourses = listOfCourses;
        this.ratingOfCourses = ratingOfCourses;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getGender() {
        return gender;
    }

    public int getStudyHours() {
        return studyHours;
    }

    public String getPartTimeJob() {
        return partTimeJob;
    }

    public int getMath() {
        return math;
    }

    public int getPhysics() {
        return physics;
    }

    public int getChemistry() {
        return chemistry;
    }

    public String getGrade() {
        return grade;
    }

    public String getComment() {
        return comment;
    }

    public String getCourseRecommendation() {
        return courseRecommendation;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getListOfCourses() {
        return listOfCourses;
    }

    public int getRatingOfCourses() {
        return ratingOfCourses;
    }
}