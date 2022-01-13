package domain.objects.obstacles;

public class FactoryObstacle {

    private static FactoryObstacle instance;

    private FactoryObstacle() {
    }

    public static FactoryObstacle getInstance() {
        if (instance == null) {
            instance = new FactoryObstacle();
        }
        return instance;
    }

    public Obstacle createObstacle(String type) {

        return switch (type) {
            case "simple" -> new ObstacleSimple();
            case "firm" -> new ObstacleFirm();
            case "explosive" -> new ObstacleExplosive();
            case "gift" -> new ObstacleGift();
            default -> null;
        };

    }
}