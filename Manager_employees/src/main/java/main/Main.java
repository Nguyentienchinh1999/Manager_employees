package main;

import Utils.util;
import dao.AccountDAO;
import dao.DepartmentsDAO;
import dao.EmployeesDAO;
import dao.PositionDAO;
import model.Departments;
import model.Employees;
import model.Positions;
import org.w3c.dom.ls.LSOutput;

import java.security.PublicKey;
import java.sql.SQLOutput;
import java.util.*;

public class Main {
    private static List<Employees> employeesList = new ArrayList<>();
    private static List<Departments> departmentsList = new ArrayList<>();
    private static List<Positions> positionsList = new ArrayList<>();
    private static EmployeesDAO employeesDAO = new EmployeesDAO();
    private static DepartmentsDAO departmentsDAO = new DepartmentsDAO();
    private static PositionDAO positionDAO = new PositionDAO();
    private static Utils.util util = new util();
    private static Scanner in = new Scanner(System.in);

    private static void mainMenu() {
        System.out.println("--- QUẢN LÝ Nhân Viên---");
        System.out.println("1. Danh sách nhân viên");
        System.out.println("2. Danh sách phòng ban: ");
        System.out.println("3. Nhập 1 nhân viên mới");
        System.out.println("4.  Cập nhật thông tin nhân viên: ");
        System.out.println("5. Xóa một nhân viên theo mã: ");
        System.out.println("6. Tìm một nhân viên theo họ tên:");
        System.out.println("7. Thêm nhân viên vào phòng ban");
        System.out.println("8. Xóa nhân viên khỏi phòng ban");
        System.out.println("9. Chuyển phòng ban cho 1 nhân viên: ");
        System.out.println("10. Thêm phòng ban: ");
        System.out.println("11. Sửa phòng ban: ");
        System.out.println("12. Xóa phong ban: ");
        System.out.println("13. Tính thuế thu nhập cá nhân cho 1 nhân viên bất kỳ: ");
        System.out.println("14. Xem nhân viên có lương cao nhất: ");
        System.out.println("15. Thoát;");
    }
    private static void runMainMenu(){
        int option = -1;
        util.checkAccount();
        do {
            mainMenu();
            System.out.print("Nhập lựa chọn: ");
            try {
                option = Integer.parseInt(in.nextLine());
            } catch (Exception e) {
                System.out.println("Nhập sai định dạng!");
                continue;
            }
            if (option < 0 || option > 15) {
                System.out.println("Lựa chọn không hợp lệ");
                continue;
            }
            // Xu ly cac TH thoa man
            switch (option) {
                case 1:
                    showEmployees();
                    break;
                case 2:
                    showDepartments();
                    break;
                case 3:
                    insertEmployees();
                    break;
                case 4:
                    updateEmployees();
                    break;
                case 5:
                    deleteEmployes();
                    break;
                case 6:
                    seartchEmployees();
                    break;
                case 7:
                    insertEmployeeToDepartment();
                    break;
                case 8:
                    deleteEmployeeInDepratment();
                    break;
                case 9:
                    changeDepartment();
                    break;
                case 10:
                    personalIncomeTax();
                    break;
                case 11:
                    insertDepartment();
                    break;
                case 12:
                    updateDepartment();
                    break;
                case 13:
                    deleteDepartment();
                    break;
                case 14:
                    showSalaryMax();
                case 15:
                    System.out.println("Đã Thoát");
                    break;
            }
        }
        while (option != 15);
        in.close();
    }
    private static void startMain(){
        System.out.println("\t 1. Đăng ký tài khoản: ");
        System.out.println("\t 2. Đăng nhập:");
        int option = -1;
        boolean flag = false;
        while (!flag){
            try{
                option = Integer.parseInt(in.nextLine());
                if(option < 1 || option > 2){
                    System.out.print("\t Nhập sai cấu trúc, mời nhập lại: ");
                }else {
                    switch (option){
                        case 1: util.registerAccount();
                        runMainMenu();
                            break;
                        case 2: runMainMenu();
                            flag = true;
                        break;
                    }
                }
            }catch (Exception e){
                System.out.println("Nhập sai định dạng, mời nhập lại: ");
                continue;
            }
        }
    }
    private static void showEmployees() {
        employeesList = employeesDAO.getAll();
        employeesList.stream().forEach(employees -> {
            System.out.println("ID: " + employees.getEmployee_id() + ", Name: " + employees.getName() + ", Salary: " + employees.getSalary() +
                    ", Gender: " + employees.getGender() + ", Department_id: " + employees.getDepartment_id() + ", Position_id: " + employees.getPosition_id());
        });

    }

    private static void showDepartments() {
        departmentsList = departmentsDAO.getAll();
        String leftAlignFormat = "| %-3s | %-20s | %n";
        System.out.format("+-----+----------------------+%n");
        System.out.format("| ID  |  Department_name     |%n");
        System.out.format("+-----+----------------------+%n");
        for (int i = 0; i < departmentsList.size(); i++) {
            System.out.format(leftAlignFormat, departmentsList.get(i).getDepartment_id(), departmentsList.get(i).getName());
        }
        System.out.format("+-----+----------------------+%n");
    }

    private static void insertEmployees() {
        Employees employees = new Employees();
        System.out.println("THÊM 1 NHÂN VIÊN: ");

//      Nhập Tên NV:
        System.out.print("\t Nhập tên NV: ");
        String name = in.nextLine();
        util.whileInputNotNull(name);
        employees.setName(name);

//        Nhập Giới tính
        System.out.print("\t Nhập giới tính: ");
        int gender = util.whileInputGender();
        employees.setGender(gender);


//        Nhập date
        System.out.print("\t nhập ngày tháng năm sinh:");
        String date = util.checkDate();
        employees.setDate(date);

//        Nhập Email
        System.out.print("\t nhập email: ");
        String email;
        email = in.nextLine();
        util.whileInputNotNull(email);
        util.whileEmail(email);
        employees.setEmail(email);

//        Nhập số đt
        System.out.print("\t nhập số đt: ");
        String phone = util.checkInputPhone();
        employees.setPhone_number(phone);

//        Nhập địa chỉ
        System.out.print("\t Nhập địa chỉ: ");
        employees.setAddress(in.nextLine());


//       Show bộ phận và chọn theo id
//        util.selectDepartmentsAndPosition(employees);

//        Nhập Lương:
        System.out.print("\t Nhập lương: ");
        int salary = util.checkSalary();
        employees.setSalary(salary);

//        dùng hàm insert để thêm dữ liệu vào database
        employeesDAO.insert(employees);
        System.out.println("\t Thêm thành công: ");
    }

    private static void updateEmployees() {
        Employees employees = new Employees();

        System.out.println("Cập nhật thông tin nhân viên: ");
        System.out.print("\t Nhập ID nhân viên cần update: ");

//        Kiểm tra xem có nhân vien nao co ID nay hay khong
        int employy_id = util.checkEmployeesById();

//        Nhập Tên NV:
        System.out.print("\t Nhập tên NV: ");
        String name = in.nextLine();
        util.whileInputNotNull(name);
        employees.setName(name);

//        Nhập Giới tính
        System.out.print("\t Nhập giới tính: ");
        int gender = util.whileInputGender();
        employees.setGender(gender);


//        Nhập date
        System.out.print("\t nhập ngày tháng năm sinh:");
        String date = util.checkDate();
        employees.setDate(date);

//        Nhập Email
        System.out.print("\t nhập email: ");
        String email;
        email = in.nextLine();
        util.whileInputNotNull(email);
        util.whileEmail(email);
        employees.setEmail(email);

//        Nhập số đt
        System.out.print("\t nhập số đt: ");
        String phone = util.checkInputPhone();
        employees.setPhone_number(phone);

//        Nhập địa chỉ
        System.out.print("\t Nhập địa chỉ: ");
        employees.setAddress(in.nextLine());


//        Nhập Lương:
        System.out.print("\t Nhập lương: ");
        int salary = util.checkSalary();
        employees.setSalary(salary);

        employeesDAO.update(employees, employy_id);
        System.out.print("\t Update Thành Công");

    }

    private static void deleteEmployes() {
        System.out.print("\t Nhập ID nhân viên cần xóa: ");
        int emoloyye_id = util.checkEmployeesById();

        employeesDAO.delete(emoloyye_id);
        System.out.print("\t Xoa Thành Công");
    }

    private static void seartchEmployees() {
        System.out.print("\t Nhập ID nhân viên cần tìm: ");
        int employee_id = util.checkEmployeesById();

        Employees employees = employeesDAO.getById(employee_id);
        System.out.println("ID: " + employees.getEmployee_id() + ", Name: " + employees.getName() + ", Department_id: " + employees.getDepartment_id() + ", Position_id: " + employees.getPosition_id());
    }
    private static void insertEmployeeToDepartment() {
        Employees employees = new Employees();
        System.out.print("\t Nhập ID nhân viên cần thêm vào phòng ban: ");
        int emoloyee_id =  util.checkDepartmentIdInEmployyes(employees);
        employeesDAO.updateDepartmentId(employees, emoloyee_id);
        System.out.println("\t Thêm nhân viên vào phòng ban thành công");
    }

    private static void deleteEmployeeInDepratment(){
        System.out.print("\t Nhập ID nhân viên cần xóa khỏi phòng ban: ");
        int employee_id = util.checkDeleteDepartmentIdEmployees();
        employeesDAO.deleteDepartmentID(employee_id);
        System.out.println("\t Xóa nhân vien khoi phòng ban thành công");
    }

    private static void changeDepartment(){
        Employees employees = new Employees();
        System.out.print("\t Nhập ID nhân viên cần chuyển phòng ban: ");
        int emplyee_id =  util.checkChangeDepartment(employees);
        employeesDAO.updateDepartmentId(employees, emplyee_id);
        System.out.println("\t Cập nhật thành công: ");
    }

    private static void insertDepartment(){
        Departments departments = new Departments();
        System.out.print("\t Nhập tên phòng ban: ");
        String name = in.nextLine();
        util.whileInputNotNull(name);
        departments.setName(name);
        departmentsDAO.insert(departments);
        System.out.println("\t Thêm Thành Công: ");
    }
    private static void updateDepartment(){
        Departments departments = new Departments();
        System.out.print("\t Nhập Id phòng ban muốn cập nhật: ");
        int departmentId = util.checkDepartmentByID();
        System.out.print("\t Nhập tên phòng ban: ");
        String name = in.nextLine();
        util.whileInputNotNull(name);
        departments.setName(name);
        departmentsDAO.update(departments, departmentId);
        System.out.println("\t Cập nhật thành công");
    }

    private static void deleteDepartment(){
        System.out.print("\t Nhập ID phòng ban muốn xóa: ");
        int departmentId = util.checkDepartmentByID();
        util.checkDeleteDepartment(departmentId);
        System.out.println("\t Xóa Thành Công");

    }
    private static void personalIncomeTax(){
        System.out.println("\t Nhập ID nhân viên cần tính thuế: ");
        int employeeId = util.checkEmployeesById();
        util.checkPersonalIncomeTax(employeeId);

    }
    private static void showSalaryMax(){

      Employees employees = employeesDAO.showSalaryMax();
        System.out.println("ID: " + employees.getEmployee_id() + ", Tên: " + employees.getName() + ", Lương: " + employees.getSalary());
    }
    public static void main(String[] args) {
       startMain();
    }
}
