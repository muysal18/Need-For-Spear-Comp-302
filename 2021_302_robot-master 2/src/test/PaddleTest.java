package test;


import domain.objects.Paddle;
import domain.objects.obstacles.FactoryObstacle;
import domain.objects.obstacles.Obstacle;
import domain.objects.obstacles.ObstacleFirm;
import domain.objects.obstacles.ObstacleGift;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PaddleTest {

    static Paddle paddle;
    static Random random;

    private static int width = 40;
    private static int posX = 100;

    @BeforeAll
    static void setUp() {
        paddle = Paddle.getInstance(/*width, posX*/);
        random = new Random();
    }

    @Test
    public void getInstanceTest() {
        assertNotNull(paddle, "Get instance returns null");
    }

    @Test
    public void fieldsTest() {
        assertEquals(width, paddle.getWidth());
        assertEquals(posX, paddle.getX());
    }

    @Test
    public void moveTest() {
        int dx = random.nextInt(10);
        int oldX = paddle.getX();

        if (random.nextBoolean()) { // left
            paddle.moveLeft(dx);
            assertEquals(paddle.getX(), oldX - dx);
            posX -= dx;
        } else {
            paddle.moveRight(dx);
            assertEquals(paddle.getX(), oldX + dx);
            posX += dx;
        }
    }

    @Test
    public void yTest() {
        assertEquals(paddle.getY(), 450);
    }

    @Test
    public void performanceTest() {
        long start = System.currentTimeMillis();
        for(int i=0 ; i<1000000 ; i++){
            paddle.moveLeft(5);
            paddle.moveRight(5);
        }
        long end = System.currentTimeMillis();
        long elapsedTime = end - start;
        assertTrue(elapsedTime < 100 );
    }

}
