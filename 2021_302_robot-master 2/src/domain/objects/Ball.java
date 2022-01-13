package domain.objects;

import java.awt.*;

public class Ball {

    private final int height;
    private final int width;
    private final Rectangle ballRect;
    private double ballposX;
    private double ballposY;
    private int ballXdir;
    private int ballYdir;
    private double speed;
    private int remainingSlownessTime;
    private int remainingUnstoppableTime;
    private int damage;

    public Ball(int height, int width, int startPosX, int startPosY, int ballXdir, int ballYdir) {
        this.height = height;
        this.width = width;
        ballposX = startPosX;
        ballposY = startPosY;
        this.ballXdir = ballXdir;
        this.ballYdir = ballYdir;
        this.speed = 1;
        this.remainingSlownessTime = 0;
        this.ballRect = new Rectangle(startPosX, startPosY - 18, 16, 16);
    }

    public void drawBall(Graphics2D g2) {
        g2.setColor(Color.red);
        g2.fillOval(getBallposX(), getBallposY(), 16, 16);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getBallposX() {
        return (int) Math.round(ballposX);
    }

    public int getBallposY() {
        return (int) Math.round(ballposY);
    }

    public int getBallXdir() {
        return ballXdir;
    }

    public int getBallYdir() {
        return ballYdir;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void move() {
        ballposX = ballposX + ballXdir * speed;
        ballposY = ballposY + ballYdir * speed;
        setBallRect(getBallposX(), getBallposY());
    }

    public void updateTime(Abilities abilities, int decreaseTime) {
        remainingSlownessTime -= decreaseTime;
        remainingUnstoppableTime -= decreaseTime;
        if (remainingSlownessTime < 0)
            speed = 1;
        if (remainingUnstoppableTime <= 0) abilities.deactivateUnstoppableEnchantedSphere(this);
    }

    public void startSlowness(int slownessTime) {
        speed = 0.5f;
        remainingSlownessTime = slownessTime;
    }

    public void startUnstoppable(int unstoppableTime) {
        remainingUnstoppableTime = unstoppableTime;
    }

    public void reverseDirX() {
        ballXdir *= -1;
    }

    public void reverseDirY() {
        ballYdir *= -1;
    }

    public Rectangle getBallRect() {
        return ballRect;
    }

    public void setBallRect(int x, int y) {
        this.ballRect.setLocation(x, y);
    }

}
