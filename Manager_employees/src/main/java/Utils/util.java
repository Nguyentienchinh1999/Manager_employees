package Utils;

import dao.AccountDAO;
import dao.DepartmentsDAO;
import dao.EmployeesDAO;
import dao.PositionDAO;
import model.Account;
import model.Departments;
import model.Employees;
import model.Positions;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;

public class util {
    public static List<Employees> employeesList = new ArrayList<>();
    public static List<Departments> departmentsList = new ArrayList<>();
    public static List<Positions> positionsList = new ArrayList<>();
    public static EmployeesDAO employeesDAO = new EmployeesDAO();
    public static DepartmentsDAO departmentsDAO = new DepartmentsDAO();
    public static PositionDAO positionDAO = new PositionDAO();
    public static AccountDAO accountDAO = new AccountDAO();
    public static Scanner in = new Scanner(System.in);


    public void registerAccount()   {
        System.out.print("\t Nhập vào tên tài khoản: ");
        String username = in.nextLine();
        whileInputNotNull(username);
        System.out.print("\t Nhập vào mật khẩu: ");
        String password = in.nextLine();
        System.out.print("\t Nhập lại mật khâủ: ");
        String rePassword = in.nextLine();
        while (!password.equals(rePassword)){
            System.out.println("password nhập lại không đúng, vui lòng nhập lại password: ");
             rePassword = in.nextLine();
        }
        Account account  = new Account();
        account.setUser_name(username);
        account.setPass_word(password);
        accountDAO.registerAcoount(account);
        System.out.println("\t Đăng ký tài khoản thành công: ");
    }
    public void checkAccount() {
        AccountDAO accountDAO = new AccountDAO();
        String username = "", password = "";
        System.out.println("ĐĂNG NHẬP");
        int count = 3;
        boolean isLogin = false;
        while (count != 0) {
            count = count - 1;
            System.out.print("\t Nhập username:");
            username = in.nextLine();
            System.out.print("\t Nhập password: ");
            password = in.nextLine();
            if (accountDAO.isAccount(username, password)) {
                isLogin = true;
                break;
            }
            if (count == 0) {
                break;
            } else {
                System.out.printf("\t Bạn còn %d lần nhập!\n", count);
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
                    System.out.print("\t phai la 0 hoac 1");
                }
            }catch (Exception e){
                System.out.println("\t Nhập sai định dạng, Mời nhập lại: ");
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
               System.out.println("\t không đúng định dạng, mời nhập lại: ");
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
            System.out.println("\t Đã có Email này, vui lòng nhập lại: ");
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
                   System.out.print("\t Phải nhập đúng 10 sô, mời nhập lại: ");
               }

           }catch (Exception e){
               System.out.println("\t Nhập sai định dang; mời nhập lại: ");
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
                    System.out.print("\t phải nhập số dương");
                }
            }catch (Exception e){
                System.out.println("\t Nhập sai định dạng, Mời nhập lại: ");
                continue;
            }
        }
        return salary;
    }

    public void  selectDepartmentsAndPosition(Employees employees){
        departmentsList  = departmentsDAO.getInforManager();
        departmentsList.stream().forEach(departments -> {
            System.out.println("\t department_id: " + departments.getDepartment_id() + " department_name: " + departments.getName() + " manager: " + departments.getManager());
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
                     System.out.print("\t không có bộ phận này, mời nhập lại: ");
                 }
            }catch (Exception e){
                System.out.println("\t Nhap sai định dạng, mời nhập lại: ");
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
    public void selectDepartmentsDifferent(Employees employees, int employeeId){
        Employees employees1 = employeesDAO.getById(employeeId);
        departmentsList  = departmentsDAO.getInforManager();
        departmentsList.stream().forEach(departments -> {
            System.out.println("\t department_id: " + departments.getDepartment_id() + " department_name: " + departments.getName() + " manager: " + departments.getManager());
        });
        System.out.print("\t Chọn bộ phận");
        int department_id  = -1;
        boolean flag = false;
        while (!flag){
            try{
                department_id = Integer.parseInt(in.nextLine());
                Departments departments = departmentsDAO.getById(department_id);
                if(departments != null && departments.getDepartment_id() != employees1.getDepartment_id()){
                    flag = true;
                }
                else{
                    System.out.print("\t Nhập không đúng bộ phận, mời nhap lại: ");
                }
            }catch (Exception e){
                System.out.println("\t Nhap sai định dạng, mời nhập lại: ");
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
                   System.out.print("\t Không có nhân viên nào trùng voi ID này, mời nhap lại: ");
               }
           }catch (Exception e){
               System.out.println("\t Nhập sai định dạng, mời nhập lại: ");
           }
        }
        return employee_id;
    }
    public int checkDepartmentByID(){
        boolean flag = false;
        int department_id = -1;
        while (!flag){
            try{
                department_id = Integer.parseInt(in.nextLine());
                Departments departments = departmentsDAO.getById(department_id);
                if(departments != null){
                    flag = true;
                }else {
                    System.out.println("\t Không có phòng ban nào trung voi ID này, mời nhập lại: ");
                }
            }catch (Exception e){
                System.out.println("\t Nhập sai định dạng, mời nhập lại: ");
                continue;
            }
        }
        return department_id;
    }

    public int checkDepartmentIdInEmployyes(Employees employees){
        int employee_id = -1;
        boolean flag = false;
        while (!flag) {
            try{
                employee_id = checkEmployeesById();
                Employees employeesById = employeesDAO.getById(employee_id);
                if(employeesById.getDepartment_id() == 0){
                    System.out.print("Nhân viên này chưa có phòng ban, bạn có thể thêm nhân viên vào phòng ban bất kỳ: ");
                    selectDepartmentsAndPosition(employees);
                    flag = true;
                }else {
                    System.out.print("\t Nhân viên này đã có phòng ban rồi nên không thể thêm vào phòng ban nào khác:");
                    System.out.print("\t Nhập lại ID nhân vien");
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

    public int checkChangeDepartment(Employees employees){
        int employee_id = -1;
        boolean flag = false;
        while (!flag){
            try{
                employee_id = checkEmployeesById();
                Employees employeesById = employeesDAO.getById(employee_id);
                if(employeesById.getDepartment_id() == 0){
                    System.out.println("\t Nhân viên này chưa có phòng ban, nên không thể chuyển, mời nhập lại ID nhân viên: ");
                }else {
                    if(employeesById.getPosition_id() == 1){
                        System.out.println("\t Nhân viên này là trưởng phòng, bạn cần chọn 1 nhân viên trong phòng này để tiếp quản vị trí: ");
                        int department_id = employeesById.getDepartment_id();
                        employeesList  = employeesDAO.getNotManager(department_id);
                        employeesList.stream().forEach(employees1 -> {
                            System.out.println("\t ID Nhân Viên: " + employees1.getEmployee_id() + ", Tên: " + employees1.getName() + ", Chức vụ: " + employees1.getPosition_name());
                        });
                        System.out.println("\t Nhập vào ID nhân viên cần thay thế: ");
                        boolean flag1 = flag;
                        int finalOptionId = 1;
                        while (!flag1){
                            try {
                                final int optionId = Integer.parseInt(in.nextLine());
                                finalOptionId = optionId;
                                Optional<Employees> result =
                                        employeesList.stream()
                                                .filter(employees1 -> employees1.getEmployee_id() == optionId)
                                                .findAny();
                                if(result.isPresent()){
                                    flag1 = true;
                                }
                                else {
                                    System.out.println("\t nhập sai ID nv, mời nhập lại: ");
                                }
                            }catch (Exception e){
                                System.out.println("\t Nhập sai định dạng, mời nhập lại: ");
                                continue;
                            }
                        }
                        System.out.println("\t Chọn phòng ban muốn chuyển đến: ");
                        selectDepartmentsDifferent(employees, employee_id);
                        Employees employees2 = new Employees();
                        employees2.setPosition_id(1);
                        employeesDAO.updatePositionId(employees2,finalOptionId);
                        flag  = true;
                    }
                    else {
                        System.out.println("\t Chọn phòng ban muốn chuyển đến");
                        selectDepartmentsDifferent(employees, employee_id);
                        flag = true;
                    }
                }
            }catch (Exception e){
                System.out.println("\t Nhập sai định dạng, mời nhập lại: ");
                continue;
            }
        }
        return employee_id;
    }
    public void checkDeleteDepartment(int departmentId){
        boolean flag = false;
       Employees employees = employeesDAO.getCountInDepartment(departmentId);
       if(employees == null){
            departmentsDAO.delete(departmentId);
       } else{
            employeesDAO.deleteAllDepartmentId(departmentId);
            departmentsDAO.delete(departmentId);
       }
    }

    public void checkPersonalIncomeTax(int employeeId){
        DecimalFormat formatter = new DecimalFormat("###,###,###");

        System.out.print("\tNhập lương đóng bảo hiểm: ");
        int luongBh = in.nextInt();
        System.out.print("\tNhập số người phụ thuộc: ");
        int soNguoiPT = in.nextInt();

        //Mức đóng: BHXH (8%), BHYT (1.5%), BHTN (1%)
        //Bảo hiểm bắt buộc = luongBh x 8% + luongBh x 1.5% + luongBh x 1%
        double BHBB = (luongBh * 0.08) + (luongBh * 0.015) + (luongBh * 0.01);
        System.out.println("\t-Bảo hiểm bắt buộc = "+formatter.format(BHBB)+" VNĐ");


        // giảm trừ bản thân : 11000000
        int GTBT = 11000000;
        System.out.println("\t-Bảo hiểm bắt buộc = "+formatter.format(GTBT)+" VNĐ");

        //Giảm trừ người phụ thuộc = soNguoiPT x 4,400,000 = mặc định
        int GTNPT = soNguoiPT * 4400000;
        System.out.println("\t-Bảo hiểm bắt buộc = "+formatter.format(GTNPT)+" VNĐ");

        // Thu nhập tính thuế = lương - BHBB - GTBT - GTNPT;
        int luong = employeesDAO.getById(employeeId).getSalary();
        double TNTT = luong - BHBB - GTBT - GTNPT;
        System.out.println("\t-Thu nhập tính thuế = "+formatter.format(TNTT)+" VNĐ");

        // Thuế thu nhập cá nhân phải nộp
        double TTNCN;
        if (luong <= 5000000 ){
            TTNCN = TNTT * 0.05;
        }else if (luong <= 10000000){
            TTNCN = (TNTT * 0.1) - 250000;
        }else if (luong <= 18000000){
            TTNCN = (TNTT * 0.15) - 750000;
        }else if (luong <= 32000000){
            TTNCN = (TNTT * 0.2) - 1650000;
        }else if (luong <= 52000000){
            TTNCN = (TNTT * 0.25) - 3250000;
        }else if (luong <= 80000000){
            TTNCN = (TNTT * 0.3) - 5850000;
        }else {
            TTNCN = (TNTT * 0.35) - 9850000;
        }
        System.out.println("\t-Thuế thu nhập cá nhân phải nộp = "+formatter.format(TTNCN)+" VNĐ");
    }
}




