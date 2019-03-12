package com.example.studentmanagementsystem.model;

import java.util.Date;

public class Departments {

    private String fbDeptId;
    private String deptId;
    private String deptName;
    private Date deptCreatedDate;

    public Departments() {
    }

    public Departments(String fbDeptId, String deptId, String deptName, Date deptCreatedDate) {
        this.fbDeptId = fbDeptId;
        this.deptId = deptId;
        this.deptName = deptName;
        this.deptCreatedDate = deptCreatedDate;
    }

    public String getFbDeptId() {
        return fbDeptId;
    }

    public void setFbDeptId(String fbDeptId) {
        this.fbDeptId = fbDeptId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Date getDeptCreatedDate() {
        return deptCreatedDate;
    }

    public void setDeptCreatedDate(Date deptCreatedDate) {
        this.deptCreatedDate = deptCreatedDate;
    }
}
