package test;


import domain.objects.Ball;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BallTest {

    static Ball ball;

    private static int ballPosX = 100;
    private static int ballPosY = 200;
    private static int ballXDir = -2;
    private static int ballYDir = -1;

    @BeforeAll
    static void setUp() {
        ball = new Ball(16,16, ballPosX, ballPosY, ballXDir, ballYDir);
    }

    @Test
    public void initializeTest() {
        assertNotNull(ball, "Ball has been created successfully");
    }

    @Test
    public void fieldsTest() {
        assertEquals(16, ball.getHeight());
        assertEquals(16, ball.getWidth());
        assertEquals(ballPosX, ball.getBallposX());
        assertEquals(ballPosY, ball.getBallposY());;
        assertEquals(ballXDir, ball.getBallXdir());
        assertEquals(ballYDir, ball.getBallYdir());
    }

    @Test
    public void moveTest() {
        int oldPosX = ball.getBallposX();
        int oldPosY = ball.getBallposY();
        int dirX = ball.getBallXdir();
        int dirY = ball.getBallYdir();
        ball.move();
        ballPosX += dirX;
        ballPosY += dirY;
        assertEquals(ball.getBallposX(), oldPosX + dirX);
        assertEquals(ball.getBallposY(), oldPosY + dirY);
    }

    @Test
    public void reverseTest(){
        int dirX = ball.getBallXdir();
        ball.reverseDirX();
        ballXDir *= -1;
        assertEquals(-dirX, ball.getBallXdir());

        int dirY = ball.getBallYdir();
        ball.reverseDirY();
        ballYDir *= -1;
        assertEquals(-dirY, ball.getBallYdir());
    }

    @Test
    public void performanceTest() {
        long start = System.currentTimeMillis();
        for(int i=0 ; i<1000000 ; i++){
            ball.reverseDirY();
            ball.move();
            ball.reverseDirX();
        }
        long end = System.currentTimeMillis();
        long elapsedTime = end - start;
        assertTrue(elapsedTime < 100 );
    }
}
