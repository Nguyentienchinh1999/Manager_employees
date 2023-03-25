package dao;

import connection.MyConnection;

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
}
