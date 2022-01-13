package domain.database;

import domain.Controller;
import domain.objects.obstacles.Obstacle;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class LoadMap {
    public ArrayList<String> mapIDList = new ArrayList<>();
    Connection connection;
    Controller controller = Controller.getInstance();

    public LoadMap() throws SQLException {
        connection = connect();
        exists();
    }

    public Connection connect() throws SQLException {
        String host = "ec2-54-73-152-36.eu-west-1.compute.amazonaws.com";
        String database = "daj79qig51jba3";
        String url = "jdbc:postgresql://" + host + "/" + database;
        String user = "txrnpayubogotd";
        String password = "71eee9c2342118989eee3a3079e78a93291b6564de098fbcafcb1f7466596459";

        return DriverManager.getConnection(url, user, password);
    }

    public void exists() throws SQLException {
        String str = "SELECT DISTINCT map_id FROM maps";
        ResultSet rs = connection.createStatement().executeQuery(str);
        while (rs.next()) {
            if (!mapIDList.contains(rs.getString(1)))
                mapIDList.add(rs.getString(1));
        }
    }

    public void getData(String str) throws SQLException {
        String query = "SELECT * FROM maps WHERE map_id= '" + str + "'";
        ResultSet rs = connection.createStatement().executeQuery(query);
        ArrayList<Obstacle> list = new ArrayList<>();
        while (rs.next()) {
            Obstacle o = controller.addNewObstacle(rs.getString("obstacle_type"));
            o.setCoordinates(new Point(rs.getInt("x"), rs.getInt("y")));
            o.setFirmness(rs.getInt("firmness"));
            o.setGift(rs.getBoolean("gift"));
            o.setMovement(rs.getBoolean("movement"));
            list.add(o);
        }
        controller.setRunModeList(list);
    }

}
