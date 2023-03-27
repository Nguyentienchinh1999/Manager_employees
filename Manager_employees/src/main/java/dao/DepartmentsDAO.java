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
        final String sql = "SELECT d.department_id, d.name, e.name\n" +
                " FROM departments d\n" +
                " LEFT JOIN employees e\n" +
                " ON d.department_id = e.department_id\n" +
                " AND e.position_id = (SELECT position_id FROM positions p WHERE p.name = 'Trưởng phòng')";
        try {
            Connection conn = MyConnection.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Departments departments = new Departments();
                departments.setDepartment_id(rs.getInt("department_id"));
                departments.setName(rs.getString("d.name"));
                departments.setManager(rs.getString("e.name"));
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
    public List<Departments> getInforManagerDifferent(int deparmentId){
        List<Departments> departmentsList = new ArrayList<>();
        final String sql = "SELECT d.department_id, d.name, e.name\n" +
                " FROM departments d\n" +
                " LEFT JOIN employees e\n" +
                " ON d.department_id = e.department_id\n" +
                " AND e.position_id = (SELECT position_id FROM positions p WHERE p.name = 'Trưởng phòng') WHERE d.department_id != '" + deparmentId + "'";
        try {
            Connection conn = MyConnection.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Departments departments = new Departments();
                departments.setDepartment_id(rs.getInt("department_id"));
                departments.setName(rs.getString("d.name"));
                departments.setManager(rs.getString("e.name"));
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

    public void insert(Departments departments){
        String sql = String.format("INSERT INTO `departments` VALUES(NULL, '%s')", departments.getName());
        try{
            Connection conn = MyConnection.getConnection();
            Statement stmt = conn.createStatement();
            long rs = stmt.executeUpdate(sql);

            if(rs == 0){
                System.out.println("Thêm thất bại");
            }
            stmt.close();
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void update(Departments departments, int id){
        String sql = String.format("UPDATE `departments` SET `name` = '%s' WHERE `department_id` = %d", departments.getName(), id);
        try {
            Connection conn = MyConnection.getConnection();
            Statement stmt = conn.createStatement();
            long rs = stmt.executeUpdate(sql);

            if (rs == 0) {
                System.out.println("Cập nhật thất bại");
            }
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void delete(int id){
        Departments departments = getById(id);
        final String sql = "DELETE FROM `departments` WHERE  `department_id` = '" + id + "'";
        try {
            Connection conn = MyConnection.getConnection();
            Statement stmt = conn.createStatement();
            long rs = stmt.executeUpdate(sql);

            if (rs == 0) {
                System.out.println("Xoá thất bại");
            }
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
