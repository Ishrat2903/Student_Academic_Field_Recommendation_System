package com.programming;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StudentRecommendationGUI extends JFrame {

    private JTextField studentIdField;
    private JTextArea resultArea;
    private JButton recommendButton;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;

    public StudentRecommendationGUI() {

        setTitle("Student Course Recommendation");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ============================================================
        // Main GUI Layout
        // ============================================================

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        // ============================================================
        // TITLE
        // ============================================================

        JLabel titleLabel = new JLabel(
                "Student Course Recommendation System",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 30)
        );

        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // ============================================================
        // TOP PANEL
        // ============================================================

        JPanel topPanel = new JPanel();

        topPanel.setLayout(
                new BoxLayout(topPanel, BoxLayout.Y_AXIS)
        );

        // ============================================================
        // STUDENT ID SEARCH PANEL
        // ============================================================

        JPanel searchPanel = new JPanel(
                new FlowLayout()
        );

        JLabel studentIdLabel =
                new JLabel("Enter Student ID:");

        studentIdLabel.setFont(
                new Font("Arial", Font.PLAIN, 18)
        );

        studentIdField =
                new JTextField(12);

        studentIdField.setFont(
                new Font("Arial", Font.PLAIN, 18)
        );

        recommendButton =
                new JButton("Get Recommendations");

        recommendButton.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        searchPanel.add(studentIdLabel);
        searchPanel.add(studentIdField);
        searchPanel.add(recommendButton);

        topPanel.add(searchPanel);

        // ============================================================
        // CRUD BUTTON PANEL
        // ============================================================

        JPanel crudPanel = new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER,
                        15,
                        5
                )
        );

        addButton =
                new JButton("Add Student");

        updateButton =
                new JButton("Update Student");

        deleteButton =
                new JButton("Delete Student");

        addButton.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        updateButton.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        deleteButton.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        crudPanel.add(addButton);
        crudPanel.add(updateButton);
        crudPanel.add(deleteButton);

        topPanel.add(crudPanel);

        mainPanel.add(
                topPanel,
                BorderLayout.NORTH
        );

        // ============================================================
        // RESULT AREA
        // ============================================================

        resultArea = new JTextArea();

        resultArea.setEditable(false);

        resultArea.setFont(
                new Font("Arial", Font.PLAIN, 17)
        );

        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        JScrollPane scrollPane =
                new JScrollPane(resultArea);

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        setContentPane(mainPanel);

        // ============================================================
        // BUTTON ACTIONS
        // ============================================================

        recommendButton.addActionListener(
                e -> getRecommendations()
        );

        addButton.addActionListener(
                e -> showAddStudentDialog()
        );

        updateButton.addActionListener(
                e -> showUpdateStudentDialog()
        );

        deleteButton.addActionListener(
                e -> deleteStudent()
        );
    }


    // ================================================================
    // READ - GET RECOMMENDATIONS
    // ================================================================

    private void getRecommendations() {

        String input = studentIdField.getText().trim();

        if (input.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a student ID."
            );

            return;
        }

        try {

            int studentId = Integer.parseInt(input);

            // --------------------------------------------------------
            // Get student from database
            // --------------------------------------------------------

            StudentDAO studentDAO = new StudentDAO();

            Student selectedStudent =
                    studentDAO.getStudentById(studentId);

            if (selectedStudent == null) {

                resultArea.setText(
                        "Student with ID " +
                                studentId +
                                " was not found."
                );

                return;
            }

            // --------------------------------------------------------
            // Generate recommendations using the formula
            // --------------------------------------------------------

            RecommendationEngine engine =
                    new RecommendationEngine();

            List<String> recommendations =
                    engine.generateRecommendations(
                            selectedStudent.getMath(),
                            selectedStudent.getPhysics(),
                            selectedStudent.getChemistry()
                    );

            // --------------------------------------------------------
            // Display student information
            // --------------------------------------------------------

            StringBuilder result =
                    new StringBuilder();

            result.append(
                    "STUDENT INFORMATION\n"
            );

            result.append(
                    "================================\n\n"
            );

            result.append("Student ID: ")
                    .append(selectedStudent.getStudentId())
                    .append("\n");

            result.append("Name: ")
                    .append(selectedStudent.getStudentName())
                    .append("\n");

            result.append("Phone: ")
                    .append(selectedStudent.getPhoneNo())
                    .append("\n");

            result.append("Gender: ")
                    .append(selectedStudent.getGender())
                    .append("\n");

            result.append("Study Hours: ")
                    .append(selectedStudent.getStudyHours())
                    .append("\n");

            result.append("Part-Time Job: ")
                    .append(selectedStudent.getPartTimeJob())
                    .append("\n");

            result.append("Math: ")
                    .append(selectedStudent.getMath())
                    .append("\n");

            result.append("Physics: ")
                    .append(selectedStudent.getPhysics())
                    .append("\n");

            result.append("Chemistry: ")
                    .append(selectedStudent.getChemistry())
                    .append("\n");

            result.append("Grade: ")
                    .append(selectedStudent.getGrade())
                    .append("\n");

            // --------------------------------------------------------
            // Display generated recommendations
            // --------------------------------------------------------

            result.append(
                    "\nRECOMMENDED COURSES\n"
            );

            result.append(
                    "================================\n\n"
            );

            if (recommendations.isEmpty()) {

                result.append(
                        "No recommendations found."
                );

            } else {

                for (String recommendation : recommendations) {

                    result.append("• ")
                            .append(recommendation)
                            .append("\n");
                }
            }

            resultArea.setText(
                    result.toString()
            );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student ID must be a number."
            );
        }
    }

// ================================================================
// CREATE - ADD STUDENT
// ================================================================

    private void showAddStudentDialog() {

        JTextField nameField =
                new JTextField();

        JTextField phoneField =
                new JTextField();

        JTextField genderField =
                new JTextField();

        JTextField studyHoursField =
                new JTextField();

        JTextField partTimeJobField =
                new JTextField();

        JTextField mathField =
                new JTextField();

        JTextField physicsField =
                new JTextField();

        JTextField chemistryField =
                new JTextField();

        JTextField gradeField =
                new JTextField();

        JTextField commentField =
                new JTextField();


        // ------------------------------------------------------------
        // Create simple form
        // ------------------------------------------------------------

        JPanel panel =
                new JPanel(
                        new GridLayout(10, 2, 8, 8)
                );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );


        addFormRow(
                panel,
                "Student Name:",
                nameField
        );

        addFormRow(
                panel,
                "Phone No:",
                phoneField
        );

        addFormRow(
                panel,
                "Gender:",
                genderField
        );

        addFormRow(
                panel,
                "Study Hours:",
                studyHoursField
        );

        addFormRow(
                panel,
                "Part-Time Job:",
                partTimeJobField
        );

        addFormRow(
                panel,
                "Math:",
                mathField
        );

        addFormRow(
                panel,
                "Physics:",
                physicsField
        );

        addFormRow(
                panel,
                "Chemistry:",
                chemistryField
        );

        addFormRow(
                panel,
                "Grade:",
                gradeField
        );

        addFormRow(
                panel,
                "Comment:",
                commentField
        );


        // ------------------------------------------------------------
        // Displaying dialog
        // ------------------------------------------------------------

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        new JScrollPane(panel),
                        "Add New Student",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }


        // ------------------------------------------------------------
        // Validate and save the changes
        // ------------------------------------------------------------

        try {

            int studyHours =
                    Integer.parseInt(
                            studyHoursField
                                    .getText()
                                    .trim()
                    );

            int math =
                    Integer.parseInt(
                            mathField
                                    .getText()
                                    .trim()
                    );

            int physics =
                    Integer.parseInt(
                            physicsField
                                    .getText()
                                    .trim()
                    );

            int chemistry =
                    Integer.parseInt(
                            chemistryField
                                    .getText()
                                    .trim()
                    );


            StudentDAO studentDAO =
                    new StudentDAO();


            // --------------------------------------------------------
            // Add student
            // --------------------------------------------------------

            int studentId =
                    studentDAO.addStudent(
                            nameField.getText().trim(),
                            phoneField.getText().trim(),
                            genderField.getText().trim(),
                            studyHours,
                            partTimeJobField.getText().trim(),
                            math,
                            physics,
                            chemistry,
                            gradeField.getText().trim(),
                            commentField.getText().trim()
                    );


            // --------------------------------------------------------
            // Checking whether student was added
            // --------------------------------------------------------

            if (studentId != -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Student added successfully!\n" +
                                "Student ID: " + studentId
                );


                // Put the generated ID into
                // the main search field

                studentIdField.setText(
                        String.valueOf(studentId)
                );


                // Generate recommendations
                // for the newly added student

                getRecommendations();


            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Could not add student."
                );
            }


        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Study hours and marks must be numbers."
            );
        }
    }


    // ================================================================
    // UPDATE - UPDATE STUDENT
    // ================================================================

    private void showUpdateStudentDialog() {

        String input =
                studentIdField.getText().trim();

        if (input.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter a Student ID first."
            );

            return;
        }

        try {

            int studentId =
                    Integer.parseInt(input);

            StudentDAO studentDAO =
                    new StudentDAO();

            Student student =
                    studentDAO.getStudentById(
                            studentId
                    );

            if (student == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Student with ID "
                                + studentId
                                + " was not found."
                );

                return;
            }


            // Create fields

            JTextField idField =
                    new JTextField(
                            String.valueOf(
                                    student.getStudentId()
                            )
                    );

            idField.setEditable(false);


            JTextField nameField =
                    new JTextField(
                            student.getStudentName()
                    );

            JTextField phoneField =
                    new JTextField(
                            student.getPhoneNo()
                    );

            JTextField genderField =
                    new JTextField(
                            student.getGender()
                    );

            JTextField studyHoursField =
                    new JTextField(
                            String.valueOf(
                                    student.getStudyHours()
                            )
                    );

            JTextField partTimeJobField =
                    new JTextField(
                            student.getPartTimeJob()
                    );

            JTextField mathField =
                    new JTextField(
                            String.valueOf(
                                    student.getMath()
                            )
                    );

            JTextField physicsField =
                    new JTextField(
                            String.valueOf(
                                    student.getPhysics()
                            )
                    );

            JTextField chemistryField =
                    new JTextField(
                            String.valueOf(
                                    student.getChemistry()
                            )
                    );

            JTextField gradeField =
                    new JTextField(
                            student.getGrade()
                    );

            JTextField commentField =
                    new JTextField(
                            student.getComment()
                    );

            JTextField courseRecommendationField =
                    new JTextField(
                            student.getCourseRecommendation()
                    );

            JTextField courseCodeField =
                    new JTextField(
                            student.getCourseCode()
                    );

            JTextField listOfCoursesField =
                    new JTextField(
                            student.getListOfCourses()
                    );

            JTextField ratingField =
                    new JTextField(
                            String.valueOf(
                                    student.getRatingOfCourses()
                            )
                    );


            JPanel panel =
                    createStudentForm(
                            idField,
                            nameField,
                            phoneField,
                            genderField,
                            studyHoursField,
                            partTimeJobField,
                            mathField,
                            physicsField,
                            chemistryField,
                            gradeField,
                            commentField,
                            courseRecommendationField,
                            courseCodeField,
                            listOfCoursesField,
                            ratingField
                    );


            int result =
                    JOptionPane.showConfirmDialog(
                            this,
                            new JScrollPane(panel),
                            "Update Student",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE
                    );


            if (result != JOptionPane.OK_OPTION) {
                return;
            }


            int studyHours =
                    Integer.parseInt(
                            studyHoursField
                                    .getText()
                                    .trim()
                    );

            int math =
                    Integer.parseInt(
                            mathField
                                    .getText()
                                    .trim()
                    );

            int physics =
                    Integer.parseInt(
                            physicsField
                                    .getText()
                                    .trim()
                    );

            int chemistry =
                    Integer.parseInt(
                            chemistryField
                                    .getText()
                                    .trim()
                    );

            int rating =
                    Integer.parseInt(
                            ratingField
                                    .getText()
                                    .trim()
                    );


            boolean success =
                    studentDAO.updateStudent(
                            studentId,
                            nameField.getText().trim(),
                            phoneField.getText().trim(),
                            genderField.getText().trim(),
                            studyHours,
                            partTimeJobField.getText().trim(),
                            math,
                            physics,
                            chemistry,
                            gradeField.getText().trim(),
                            commentField.getText().trim(),
                            courseRecommendationField
                                    .getText()
                                    .trim(),
                            courseCodeField
                                    .getText()
                                    .trim(),
                            listOfCoursesField
                                    .getText()
                                    .trim(),
                            rating
                    );


            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Student updated successfully!"
                );

                getRecommendations();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Could not update student."
                );
            }


        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student ID, study hours, marks and rating "
                            + "must be numbers."
            );
        }
    }


    // ================================================================
    // DELETE - DELETE STUDENT
    // ================================================================

    private void deleteStudent() {

        String input =
                studentIdField.getText().trim();

        if (input.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter a Student ID first."
            );

            return;
        }

        try {

            int studentId =
                    Integer.parseInt(input);

            StudentDAO studentDAO =
                    new StudentDAO();

            Student student =
                    studentDAO.getStudentById(
                            studentId
                    );

            if (student == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Student with ID "
                                + studentId
                                + " was not found."
                );

                return;
            }


            int confirmation =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to delete student "
                                    + studentId
                                    + " ("
                                    + student.getStudentName()
                                    + ")?",
                            "Confirm Delete",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );


            if (confirmation != JOptionPane.YES_OPTION) {
                return;
            }


            boolean success =
                    studentDAO.deleteStudent(
                            studentId
                    );


            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Student deleted successfully!"
                );

                studentIdField.setText("");

                resultArea.setText(
                        "Student "
                                + studentId
                                + " has been deleted."
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Could not delete student."
                );
            }


        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student ID must be a number."
            );
        }
    }


    // ================================================================
    // UPDATE FORM
    // ================================================================

    private JPanel createStudentForm(
            JTextField idField,
            JTextField nameField,
            JTextField phoneField,
            JTextField genderField,
            JTextField studyHoursField,
            JTextField partTimeJobField,
            JTextField mathField,
            JTextField physicsField,
            JTextField chemistryField,
            JTextField gradeField,
            JTextField commentField,
            JTextField courseRecommendationField,
            JTextField courseCodeField,
            JTextField listOfCoursesField,
            JTextField ratingField) {


        JPanel panel =
                new JPanel(
                        new GridLayout(
                                15,
                                2,
                                8,
                                8
                        )
                );


        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );


        addFormRow(
                panel,
                "Student ID:",
                idField
        );

        addFormRow(
                panel,
                "Student Name:",
                nameField
        );

        addFormRow(
                panel,
                "Phone No:",
                phoneField
        );

        addFormRow(
                panel,
                "Gender:",
                genderField
        );

        addFormRow(
                panel,
                "Study Hours:",
                studyHoursField
        );

        addFormRow(
                panel,
                "Part-Time Job:",
                partTimeJobField
        );

        addFormRow(
                panel,
                "Math:",
                mathField
        );

        addFormRow(
                panel,
                "Physics:",
                physicsField
        );

        addFormRow(
                panel,
                "Chemistry:",
                chemistryField
        );

        addFormRow(
                panel,
                "Grade:",
                gradeField
        );

        addFormRow(
                panel,
                "Comment:",
                commentField
        );

        addFormRow(
                panel,
                "Course Recommendation:",
                courseRecommendationField
        );

        addFormRow(
                panel,
                "Course Code:",
                courseCodeField
        );

        addFormRow(
                panel,
                "List of Courses:",
                listOfCoursesField
        );

        addFormRow(
                panel,
                "Rating of Courses:",
                ratingField
        );


        return panel;
    }


    // ================================================================
    // FORM ROW HELPER
    // ================================================================

    private void addFormRow(
            JPanel panel,
            String label,
            JTextField field) {

        JLabel labelComponent =
                new JLabel(label);

        labelComponent.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        panel.add(labelComponent);
        panel.add(field);
    }


    // ================================================================
    // MAIN
    // ================================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            StudentRecommendationGUI gui =
                    new StudentRecommendationGUI();

            gui.setVisible(true);
        });
    }
}