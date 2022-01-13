package test;


import domain.objects.obstacles.*;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ObstacleTest {
    static ObstacleSimple simple;
    static ObstacleFirm firm;
    static ObstacleExplosive explosive;
    static ObstacleGift gift;


    @BeforeAll
    static void setUp() {
        simple = new ObstacleSimple();
        firm = new ObstacleFirm();
        explosive = new ObstacleExplosive();
        gift = new ObstacleGift();
    }
    @Test
    public void getInstanceTest(){
        FactoryObstacle factory = FactoryObstacle.getInstance();
        assertNotNull(factory,"Get instance returns null");
        assertTrue(factory instanceof FactoryObstacle,"Get instance does not return Brick Factory");
    }
    @Test
    public void createSimpleBrickTest(){
        Obstacle createdBrick =  FactoryObstacle.getInstance().createObstacle("simple");
        assertTrue(createdBrick instanceof ObstacleSimple, "Create Simple Brick does not create a wrapper brick");

    }
    @Test
    public void createFirmBrickTest(){
        Obstacle createdBrick =  FactoryObstacle.getInstance().createObstacle("firm");
        assertTrue(createdBrick instanceof ObstacleFirm, "Create Simple Brick does not create a wrapper brick");

    }

    @Test
    public void createExplosiveBrickTest(){
        Obstacle createdBrick =  FactoryObstacle.getInstance().createObstacle("explosive");
        assertTrue(createdBrick instanceof ObstacleExplosive, "Create Simple Brick does not create a wrapper brick");
        if(!createdBrick.isExplosive()){
            assertEquals(explosive,createdBrick);
        }

    }
    @Test
    public void createGiftBrickTest(){
        Obstacle createdBrick =  FactoryObstacle.getInstance().createObstacle("gift");
        assertTrue(createdBrick instanceof ObstacleGift, "Create Simple Brick does not create a wrapper brick");

    }

}
