package dao;

import connection.MyConnection;
import model.Account;

import java.sql.*;

public class AccountDAO {
    public boolean isAccount(String username, String password) {
        String sql = String.format("SELECT * FROM account WHERE user_name = '%s' AND pass_word = '%s'", username, password);
        try {
            Connection conn = MyConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if(rs.next()){
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }
    public void registerAcoount(Account account){
        final String sql =
                String.format("INSERT INTO `account` VALUES(NULL,'%s','%s')",
                       account.getUser_name(), account.getPass_word());
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
}
