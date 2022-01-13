package domain.database;

import domain.objects.obstacles.Obstacle;

import java.sql.*;
import java.util.List;

public class SaveMap {
    Connection connection;

    public SaveMap() throws SQLException {
        connection = connect();
    }

    public Connection connect() throws SQLException {
        String host = "ec2-54-73-152-36.eu-west-1.compute.amazonaws.com";
        String database = "daj79qig51jba3";
        String url = "jdbc:postgresql://" + host + "/" + database;
        String user = "txrnpayubogotd";
        String password = "71eee9c2342118989eee3a3079e78a93291b6564de098fbcafcb1f7466596459";

        return DriverManager.getConnection(url, user, password);
    }

    public boolean exists(String map_id) throws SQLException {
        String str = "SELECT DISTINCT map_id FROM maps";
        ResultSet rs = connection.createStatement().executeQuery(str);
        boolean bool = false;
        while (rs.next()) {
            if (rs.getString("map_id").equals(map_id)) {
                bool = true;
            }
        }
        return bool;
    }

    public void saveValue(List<Obstacle> obstacleList, String map_id) throws SQLException {
        String query = " INSERT INTO maps"
                + " (map_id, obstacle_type, x, y, firmness, gift, movement) VALUES "
                + "(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);

        for (Obstacle obstacle : obstacleList) {
            statement.setString(1, map_id);
            statement.setString(2, obstacle.getType());
            statement.setInt(3, obstacle.getCoordinates().x);
            statement.setInt(4, obstacle.getCoordinates().y);
            statement.setInt(5, obstacle.getFirmness());
            statement.setBoolean(6, obstacle.isGift());
            statement.setBoolean(7, obstacle.isMovement());

            statement.addBatch();
            statement.executeBatch();
        }
    }

}
