package test;


import domain.Controller;
import domain.database.LoadMap;
import domain.database.SaveMap;
import domain.objects.obstacles.*;

import org.junit.jupiter.api.*;

import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SaveLoadTest {

    static LoadMap loadMap;
    static SaveMap saveMap;
    static Controller controller = Controller.getInstance();


    @BeforeAll
    static void setUp() throws SQLException {
        loadMap = new LoadMap();
        saveMap = new SaveMap();
    }
    @Test
    public void test1() throws SQLException {
        Connection connection = loadMap.connect();
        assertTrue(connection.isValid(0));
    }
    @Test
    public void test2() throws SQLException {
        assertTrue(saveMap.exists("efe"));
    }
    @Test
    public void test3() throws SQLException {
        controller.addObstacle("simple");
        controller.addObstacle("firm");
        controller.addObstacle("explosive");
        controller.addObstacle("gift");
        ArrayList<Obstacle> list = new ArrayList<>(controller.obstacles.values());
        list.get(0).setCoordinates(new Point(100,100));
        list.get(1).setCoordinates(new Point(100,100));
        list.get(2).setCoordinates(new Point(100,100));
        list.get(3).setCoordinates(new Point(100,100));
        saveMap.saveValue(list, "test");
        assertTrue(saveMap.exists("test"));
    }

   @Test
    public void test4() throws SQLException {
       loadMap.getData("efe");
       assertEquals(104, controller.obstacles.size());

   }
    @Test
    public void test5(){
        assertEquals(3, loadMap.mapIDList.size());
    }

}
