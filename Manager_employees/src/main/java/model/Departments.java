package model;

public class Departments {
    private int department_id;
    private String name;
    private String manager;
    public Departments() {
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Departments(int department_id, String name) {
        this.department_id = department_id;
        this.name = name;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override
    public String toString() {
        return "Departments{" +
                "department_id=" + department_id +
                ", name='" + name + '\'' +
                '}';
    }
}
