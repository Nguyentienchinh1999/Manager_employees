package dao;

import connection.MyConnection;
import model.Departments;
import model.Positions;

import javax.swing.text.Position;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PositionDAO {
    public List<Positions> getAll(){
        final String sql = "SELECT position_id, name FROM positions";

        List<Positions> positionsList = new ArrayList<>();

        try {
            Connection conn = MyConnection.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Positions positions = new Positions();
                positions.setPositon_id(rs.getInt("position_id"));
                positions.setName(rs.getString("name"));
                positionsList.add(positions);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return positionsList;
    }
    public List<Positions> getHasManager(){
        List<Positions> positionsList = new ArrayList<>();
        final String sql = "SELECT position_id, name FROM positions WHERE position_id != 1";
        try {
            Connection conn = MyConnection.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Positions positions = new Positions();
                positions.setName(rs.getString("name"));
                positions.setPositon_id(rs.getInt("position_id"));
                positionsList.add(positions);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return positionsList;
    }

}
