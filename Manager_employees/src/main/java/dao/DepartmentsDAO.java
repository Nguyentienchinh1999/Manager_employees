package dao;

import connection.MyConnection;
import model.Departments;
import model.Employees;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DepartmentsDAO {
    public List<Departments> getAll(){

        final String sql = "SELECT * FROM `departments`";

        List<Departments> departmentsList = new ArrayList<>();

        try {
            Connection conn = MyConnection.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Departments departments = new Departments();
                departments.setDepartment_id(rs.getInt("department_id"));
                departments.setName(rs.getString("name"));
                departmentsList.add(departments);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return departmentsList;
    }

    public List<Departments> getInforManager(){
        List<Departments> departmentsList = new ArrayList<>();
        final String sql = "SELECT d.department_id, name as 'department_name',\n" +
                "       (SELECT e.name\n" +
                "        FROM employees e\n" +
                "        JOIN positions p ON e.position_id = p.position_id\n" +
                "        WHERE e.department_id = d.department_id AND p.name = 'Trưởng Phòng') as 'manager'\n" +
                "FROM departments d";
        try {
            Connection conn = MyConnection.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Departments departments = new Departments();
                departments.setDepartment_id(rs.getInt("department_id"));
                departments.setName(rs.getString("department_name"));
                departments.setManager(rs.getString("manager"));
                departmentsList.add(departments);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return departmentsList;
    }

    public Departments getById(int id){
        Departments departments = null;
        final String sql = "SELECT * FROM `departments` WHERE  `department_id` =" + id;
        try {
            Connection conn = MyConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                 departments = new Departments();
                departments.setDepartment_id(rs.getInt("department_id"));
                departments.setName(rs.getString("name"));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return departments;
    }

}
