package com.example.studentmanagementsystem.model;

public class Students {

    private String fbStudentId;
    private String studentId;
    private String studentName;
    private String studentDepartment;

    public Students() {
    }

    public Students(String fbStudentId, String studentName, String studentId, String studentDepartment) {
        this.fbStudentId = fbStudentId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentDepartment = studentDepartment;
    }

    public String getFbStudentId() {
        return fbStudentId;
    }

    public void setFbStudentId(String fbStudentId) {
        this.fbStudentId = fbStudentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentDepartment() {
        return studentDepartment;
    }

    public void setStudentDepartment(String studentDepartment) {
        this.studentDepartment = studentDepartment;
    }
}
