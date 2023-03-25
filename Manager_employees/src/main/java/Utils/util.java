package Utils;

import dao.AccountDAO;
import dao.DepartmentsDAO;
import dao.EmployeesDAO;
import dao.PositionDAO;
import model.Departments;
import model.Employees;
import model.Positions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Spliterators;
import java.util.logging.SimpleFormatter;

public class util {
    public static List<Employees> employeesList = new ArrayList<>();
    public static List<Departments> departmentsList = new ArrayList<>();
    public static List<Positions> positionsList = new ArrayList<>();
    public static EmployeesDAO employeesDAO = new EmployeesDAO();
    public static DepartmentsDAO departmentsDAO = new DepartmentsDAO();
    public static PositionDAO positionDAO = new PositionDAO();

    public static Scanner in = new Scanner(System.in);


    public void checkAccount() {
        AccountDAO accountDAO = new AccountDAO();
        String username = "", password = "";
        System.out.println("ĐĂNG NHẬP");
        int count = 3;
        boolean isLogin = false;
        while (count != 0) {
            count = count - 1;
            System.out.print("Nhập username:");
            username = in.nextLine();
            System.out.print("Nhập password: ");
            password = in.nextLine();
            if (accountDAO.isAccount(username, password)) {
                isLogin = true;
                break;
            }
            if (count == 0) {
                break;
            } else {
                System.out.printf("Bạn còn %d lần nhập!\n", count);
            }
        }
        if (!isLogin) {
            System.out.println("Đăng nhập thất bại, bạn đã hết lượt");
            System.exit(1);
        } else {
            System.out.println("Đăng nhập thành công");
        }
    }

    public void whileInputNotNull(String input) {
        while (input == null || input.trim().isEmpty()) {
            System.out.print("\t Không được để trống trường này, mời nhập lại: ");
            input = in.nextLine();
        }
    }

    public int  whileInputGender() {
        int gender = -1;
        boolean flag = false;
        while (!flag){
            try{
                gender = Integer.parseInt(in.nextLine());
                if(gender == 0 || gender == 1){
                    flag = true;
                }else{
                    System.out.println("phai la 0 hoac 1");
                }
            }catch (Exception e){
                System.out.println("Nhập sai định dạng, Mời nhập lại: ");
                continue;
            }
        }
        return gender;
    }

    public String checkDate(){
        String date = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy");
        simpleDateFormat.setLenient(false);
        boolean flag = false;
       while (!flag){
           try{
               date = in.nextLine();
               simpleDateFormat.parse(date);
               flag = true;
           }catch (Exception e){
               System.out.println("không đúng định dạng, mời nhập lại: ");
               continue;
           }
       }
        return date;
    }

    public boolean containsValueEmail(List<Employees> employeesList, String input) {
        for (int i = 0; i < employeesList.size(); i++) {
            if (employeesList.get(i).getEmail().equalsIgnoreCase(input)) {
                return true;
            }
        }
        return false;
    }

    public void whileEmail(String email){
        employeesList  = employeesDAO.getAll();
        while (containsValueEmail(employeesList, email)){
            System.out.println("Đã có Email này, vui lòng nhập lại: ");
            email = in.nextLine();
        }
    }

    public String checkInputPhone(){
        String phone = "";
        int phoneInt;
        boolean flag = false;
       while (!flag){
           try{
               phone = in.nextLine();
               phoneInt = Integer.parseInt(phone);
               if(phoneInt > 0 && phone.length() == 10){
                   flag = true;
               }else{
                   System.out.println("Phải nhập đúng 10 sô, mời nhập lại: ");
               }

           }catch (Exception e){
               System.out.println("Nhập sai định dang; mời nhập lại: ");
               continue;
           }
       }
        return phone;
    }
    public int checkSalary(){
        boolean flag = false;
        int salary = -1;
        while (!flag){
            try{
                salary = Integer.parseInt(in.nextLine());
                if(salary > 0){
                    flag = true;
                }else{
                    System.out.println("phải nhập số dương");
                }
            }catch (Exception e){
                System.out.println("Nhập sai định dạng, Mời nhập lại: ");
                continue;
            }
        }
        return salary;
    }

    public void  selectDepartmentsAndPosition(Employees employees){
        departmentsList  = departmentsDAO.getInforManager();
        departmentsList.stream().forEach(departments -> {
            System.out.println("department_id: " + departments.getDepartment_id() + " department_name: " + departments.getName() + " manager: " + departments.getManager());
        });
        System.out.print("\t Chọn bộ phận");
        int department_id  = -1;
        boolean flag = false;
        while (!flag){
            try{
                 department_id = Integer.parseInt(in.nextLine());
                 Departments departments = departmentsDAO.getById(department_id);
                 if(departments != null){
                     flag = true;
                 }else{
                     System.out.println("không có bộ phận này, mời nhập lại: ");
                 }
            }catch (Exception e){
                System.out.println("Nhap sai định dạng, mời nhập lại: ");
                continue;
            }
        }
        employees.setDepartment_id(department_id);
        if(departmentsList.get(department_id - 1).getManager() == null) {
            System.out.println("Bộ phận này chưa có trưởng phòng, bạn có thể thêm là trưởng phòng hoặc các chức vụ khác:");
            positionsList = positionDAO.getAll();
            positionsList.stream().forEach(positions -> {
                System.out.println("\t ID: " + positions.getPositon_id() + " Chức vụ: " + positions.getName());
            });
            System.out.print("\t Chọn chức vụ: ");
            int positionId = Integer.parseInt(in.nextLine());
            while(positionId < 1 || positionId > positionsList.size()){
                System.out.println("Chọn sai ID, mời nhập lại: ");
                positionId = Integer.parseInt(in.nextLine());
            }
            employees.setPosition_id(positionId);
        }else{
            System.out.println("Bộ phận này đã có trưởng phòng, bạn chỉ có thể thêm các chức vụ sau: ");
            positionsList = positionDAO.getHasManager();
            positionsList.stream().forEach(positions -> {
                System.out.println("\t ID: " + positions.getPositon_id() + " Chức Vụ: "  +  positions.getName());
            });
            System.out.print("\t Chọn chức vụ: ");
            int positionId = Integer.parseInt(in.nextLine());
            while (positionId < 2 || positionId > positionsList.size() + 1){
                System.out.println("Chọn sai ID, mời nhập lại: ");
                positionId = Integer.parseInt(in.nextLine());
            }
            employees.setPosition_id(positionId);
        }
    }

    public int checkEmployeesById(){
        boolean flag = false;
        int employee_id = -1;
        while (!flag){
           try{
               employee_id = Integer.parseInt(in.nextLine());
               Employees employees = employeesDAO.getById(employee_id);
               if(employees != null){
                   flag = true;
               }else {
                   System.out.println("Không có nhân viên nào trùng voi ID này, mời nhap lại: ");
               }
           }catch (Exception e){
               System.out.println("Nhập sai định dạng, mời nhập lại: ");
           }
        }
        return employee_id;
    }
    public int checkDepartmentIdInEmployyes(Employees employees){
        int employee_id = -1;
        boolean flag = false;
        while (!flag) {
            try{
                employee_id = checkEmployeesById();
                Employees employeesById = employeesDAO.getById(employee_id);
                if(employeesById.getDepartment_id() == 0){
                    System.out.println("Nhân viên này chưa có phòng ban, bạn có thể thêm nhân viên vào phòng ban bất kỳ: ");
                    selectDepartmentsAndPosition(employees);
                    flag = true;
                    System.out.println(employee_id);
                }else {
                    System.out.println(employee_id);
                    System.out.println("\t Nhân viên này đã có phòng ban rồi nên không thể thêm vào phòng ban nào khác:");
                    System.out.println("\t Nhập lại ID nhân vien");
                }
            }catch (Exception e){
                System.out.println("Nhập sai định dạng, mời nhập lại: ");
                continue;
            }
        }

        return employee_id;
    }
    public int checkDeleteDepartmentIdEmployees(){
        int employee_id = -1;
        boolean flag = false;
        while (!flag){
            try{
                employee_id = checkEmployeesById();
                Employees employeesById = employeesDAO.getById(employee_id);
                if(employeesById.getDepartment_id() != 0){
                    flag = true;
                }else{
                    System.out.print("\t Nhân viên này chưa có phòng ban, mời nhập lại ID nhân viên khác");
                }
            }catch (Exception e){
                System.out.println("\t Nhập sai định dạng, mời nhập lại:  ");
                continue;
            }
        }
        return employee_id;
    }


}




