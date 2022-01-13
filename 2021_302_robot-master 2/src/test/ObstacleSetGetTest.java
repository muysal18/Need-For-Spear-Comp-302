package test;

import domain.objects.obstacles.*;
import domain.objects.Ball;

import org.junit.jupiter.api.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class ObstacleSetGetTest {
    //initializing an obstacle instance
    static Obstacle obstacle;
    static ObstacleExplosive obstacleExplosive;
    static Ball ball;


    @BeforeAll
    static void setUp() {
        //setting up for an explosive obstacle to check for its parameters
        obstacleExplosive = new ObstacleExplosive();
        obstacle = new Obstacle();
        obstacle.setExplosive(true);
        obstacle.setType("explosive");
        obstacle.setColor(Color.MAGENTA);
        obstacle.setFirmness(1);
        obstacle.setMovement(Math.random() <= 0.2);
        obstacle.setName("Pandora’s Box");
        obstacle.setWidth(15);

    }

    @Test
    public void typeTest(){
        //testing for all the parameters set earlier for an obstacle of type explosive
        //to see whether all the set methods for obstacles are working correctly
        assertEquals(obstacleExplosive.getType(), obstacle.getType());
        assertEquals(obstacleExplosive.getColor(), obstacle.getColor());
        assertEquals(obstacleExplosive.getName(), obstacle.getName());
        assertEquals(obstacleExplosive.getWidth(), obstacle.getWidth());
        assertEquals(obstacleExplosive.getFirmness(), obstacle.getFirmness());
    }

    @Test
    public void MovingTest(){
        //testing for movement of the obstacle, if the obstacle is moving stop the movement and check if the method works
        //correctly. If the obstacle does not move, start the movement and check if the method works correctly.
        if (obstacle.isMovement()) {
            obstacle.setMovement(false);
            assertEquals(false, obstacle.isMovement());
        } else {
            obstacle.setMovement(true);
            assertEquals(true, obstacle.isMovement());
        }
    }

    @Test
    public void ExplosiveTest(){
        //Check if the obstacle is explosive, if it is, check whether the method for disabling
        //the explosive boolean is working correctly.
        if (obstacle.isExplosive()) {
            obstacle.setExplosive(false);
            assertEquals(false, obstacle.isExplosive());
        } else {
            obstacle.setExplosive(true);
            assertEquals(true, obstacle.isExplosive());
        }
    }

    @Test
    public void FirmnessTest(){
        //Check for the decrease firmness method which should be called whenever an obstacle is hit by the
        //Noble Phantasm. If the method is activated, check that the firmness is decreased.
        obstacle.decreaseFirmness(1);
        assertEquals(0, obstacle.getFirmness());
        obstacle.setFirmness(3);
        assertEquals(3, obstacle.getFirmness());
    }

    @Test
    public void GiftTest(){
        //Check whether the obstacle has a property of Gift Obstacle, which it shouldn't since we initialized
        //an obstacle of type ExplosiveObstacle. Then check whether the method setGift works correctly.
        assertEquals(false, obstacle.isGift());
        obstacle.setGift(true);
        assertEquals(true, obstacle.isGift());
    }





}
