package domain;

import domain.database.LoadMap;
import domain.database.SaveMap;
import domain.objects.Ball;
import domain.objects.Paddle;
import domain.objects.obstacles.FactoryObstacle;
import domain.objects.obstacles.Obstacle;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Controller {

    private static Controller instance;
    private final Random rand = new Random();
    public boolean mapID_exists;
    public int minObstacleCountSimple = 75;
    public int minObstacleCountFirm = 10;
    public int minObstacleCountExplosive = 5;
    public int minObstacleCountGift = 10;
    public int screenWidth = 1200;
    public ArrayList<Integer> spawnLocation = new ArrayList<>();
    public HashMap<Integer, Obstacle> obstacles = new HashMap<>();
    public ArrayList<String> mapIDList = new ArrayList<>();
    private ArrayList<Obstacle> runModeList = new ArrayList<>();
    private int random_generate = -1;


    private Controller() {
        spawnLocation.add(-1);
    }

    public static Controller getInstance() {
        if (instance == null) instance = new Controller();
        return instance;
    }


    public int spawn() {
        while (spawnLocation.contains(random_generate))
            random_generate = rand.nextInt(400);
        spawnLocation.add(random_generate);
        return random_generate;
    }

    public Obstacle addNewObstacle(String str) {
        switch (str) {
            case "simple" -> {
                return FactoryObstacle.getInstance().createObstacle("simple");
            }
            case "firm" -> {
                return FactoryObstacle.getInstance().createObstacle("firm");
            }
            case "explosive" -> {
                return FactoryObstacle.getInstance().createObstacle("explosive");
            }
            case "gift" -> {
                return FactoryObstacle.getInstance().createObstacle("gift");
            }
            default -> {
                return null;
            }
        }
    }

    public void addObstacle(String type) {
        Obstacle obstacle = FactoryObstacle.getInstance().createObstacle(type);
        int loc = spawn();
        obstacle.setLocation(loc);
        obstacles.put(loc, obstacle);
    }

    public void init() {

        for (int k = 0; k < minObstacleCountSimple; k++) {
            addObstacle("simple");
        }
        for (int k = 0; k < minObstacleCountFirm; k++) {
            addObstacle("firm");
        }
        for (int k = 0; k < minObstacleCountExplosive; k++) {
            addObstacle("explosive");
        }
        for (int k = 0; k < minObstacleCountGift; k++) {
            addObstacle("gift");
        }
    }

    public void newObstacles(String str, int number) {
        for (int i = 0; i < number; i++)
            addObstacle(str);
    }

    public void initializeSpawnObstacle(ArrayList<JPanel> list) {
        if (runModeList.isEmpty()) {
            init();
            int obstacleID;
            for (int i = 1; i < spawnLocation.size(); i++) {
                obstacleID = spawnLocation.get(i);
                list.get(obstacles.get(obstacleID).getLocation()).add(obstacles.get(obstacleID).getImage());
            }
        }
    }

    public void changeLocation(int oldLocation, int newLocation) {
        obstacles.put(newLocation, obstacles.get(oldLocation));
        obstacles.get(newLocation).setLocation(newLocation);
        spawnLocation.add(newLocation);
        obstacles.remove(oldLocation);
        spawnLocation.remove(Integer.valueOf(oldLocation));
    }

    public void setObstacleCoordinates(ArrayList<JPanel> list) {
        for (int i = 1; i < spawnLocation.size(); i++) {
            obstacles.get(spawnLocation.get(i)).setCoordinates(list.get(spawnLocation.get(i)).getLocation());
        }
    }

    public void saveMap(String map_id) throws SQLException {
        SaveMap map = new SaveMap();
        List<Obstacle> obstacleList = new ArrayList<>(obstacles.values());
        mapID_exists = map.exists(map_id);
        if (!mapID_exists) map.saveValue(obstacleList, map_id);
    }

    public void maps() throws SQLException {
        LoadMap load = new LoadMap();
        for (String str : load.mapIDList) {
            if (!mapIDList.contains(str)) {
                mapIDList.add(str);
            }
        }
    }

    public void loadMap(String str) throws SQLException {
        LoadMap load = new LoadMap();
        load.getData(str);
    }

    public Game game() {
        Ball ball = new Ball(16, 16, Paddle.getInstance().getX() - 8, Paddle.getInstance().getY() - 16, -1, -2);
        ball.setDamage(1);
        return Game.getInstance(runModeList, ball);
    }

    public void setRunModeList(ArrayList<Obstacle> runModeList) {
        this.runModeList = runModeList;
    }

    public void runningModeList() {
        runModeList.addAll(obstacles.values());
    }
}
