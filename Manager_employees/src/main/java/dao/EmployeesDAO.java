package dao;

import connection.MyConnection;
import model.Departments;
import model.Employees;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeesDAO {
    public List<Employees> getAll(){

        final String sql = "SELECT * FROM `employees`";

        List<Employees> employeesList = new ArrayList<>();

        try {
            Connection conn = MyConnection.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Employees employees = new Employees();
                employees.setEmployee_id(rs.getInt("employee_id"));
                employees.setName(rs.getString("name"));
                employees.setDate(rs.getString("date"));
                employees.setGender(rs.getInt("gender"));
                employees.setEmail(rs.getString("email"));
                employees.setPhone_number(rs.getString("phone_number"));
                employees.setAddress(rs.getString("address"));
                employees.setSalary(rs.getInt("salary"));
                employees.setDepartment_id(rs.getInt("department_id"));
                employeesList.add(employees);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return employeesList;
    }

     public Employees getById(int id) {
        final String sql = "SELECT * FROM `employees` WHERE  `employee_id` = " + id  ;
        Employees employees = null;

        try {
            Connection conn = MyConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                employees = new Employees();
                employees.setEmployee_id(rs.getInt("employee_id"));
                employees.setName(rs.getString("name"));
                employees.setSalary(rs.getInt("salary"));
                employees.setDepartment_id(rs.getInt("department_id"));
                employees.setPosition_id(rs.getInt("position_id"));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees;
    }

    public void insert(Employees employees){
        final String sql =
                String.format("INSERT INTO `employees` VALUES(NULL,'%s','%s', '%s', '%s', '%s', %d, NULL , %d , NULL)",
                        employees.getName(),employees.getDate(), employees.getEmail(), employees.getPhone_number(),
                        employees.getAddress(),employees.getSalary(),employees.getDepartment_id(),employees.getGender(), employees.getPosition_id());
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

    public void update(Employees employees, int id){
        final String sql = String.format("UPDATE `employees` SET `name`='%s', `date`='%s', `email`='%s', `phone_number`='%s', `address`='%s',`salary`='%d',`gender`='%d' WHERE `employee_id`='%d'",
                employees.getName(), employees.getDate(), employees.getEmail(), employees.getPhone_number(),employees.getAddress(),employees.getSalary(),employees.getGender(),id);

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
    public void updateDepartmentId(Employees employees, int id){
        String sql = String.format("UPDATE `employees` SET `department_id`='%d', `position_id`='%d' WHERE `employee_id` = '%d' ",
                employees.getDepartment_id(), employees.getPosition_id(),id);
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
    public void deleteDepartmentID(int id){
        Employees employees = getById(id);
        final String sql = String.format("UPDATE `employees` SET `department_id` = NULL, `position_id` = NULL  WHERE `employee_id` = %d", id);

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

    public void delete(int id){
        Employees employees = getById(id);

        final String sql = "DELETE FROM `employees` WHERE  `employee_id` = '" + id + "'";
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