package com.example.inner;


public class Department {

    private int deptId;
    private String dname;
    private Employee emp1;

    public Department() {
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public Employee getEmp1() {
        return emp1;
    }

    public void setEmp1(Employee emp1) {
        this.emp1 = emp1;
    }

    @Override
    public String toString() {
        return "Department [deptId=" + deptId + ", dname=" + dname + ", emp1=" + emp1 + "]";
    }
}
