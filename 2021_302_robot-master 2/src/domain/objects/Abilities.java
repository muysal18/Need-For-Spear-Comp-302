package domain.objects;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;


public class Abilities {
    private static final int DURATION = 30000;
    public boolean unstoppableActive = false;
    public boolean expansionActive = false;
    public boolean hexActive = false;
    public ArrayList<Ball> hexBall = new ArrayList<>();
    private Paddle paddle = Paddle.getInstance();
    private int expansionAbilityCount = 0;
    private int hexAbilityCount = 0;
    private int unstoppableAbilityCount = 0;

    public Abilities() {
    }


    public void deactivateUnstoppableEnchantedSphere(Ball ball) {
        if (unstoppableActive) {
            ball.setDamage(1);
            unstoppableActive = false;
        }
    }

    public void deactivateExpansion(Paddle paddle) {
        if (expansionActive) {
            expansionActive = false;
            paddle.setWidth(paddle.getWidth() / 2);
        }
    }

    public void deactivateHex() {
        hexActive = false;
    }

    public void activateUnstoppableEnchantedSphere(Ball ball) {
        if (!unstoppableActive && unstoppableAbilityCount > 0) {
            unstoppableActive = true;
            ball.startUnstoppable(DURATION);
            ball.setDamage(999);
            unstoppableAbilityCount--;
        }
    }

    public void activateExpansion() {
        if (!expansionActive && expansionAbilityCount > 0) {
            expansionActive = true;
            paddle.startExpansion(DURATION);
            paddle.setWidth(paddle.getWidth() * 2);
            expansionAbilityCount--;
        }
    }

    public void activateHex() {
        if (!hexActive && hexAbilityCount > 0) {
            hexActive = true;
            paddle.startHex(DURATION);
            hexAbilityCount--;
        }
    }

    public void drawHex(Graphics2D g2) {
        AffineTransform old = g2.getTransform();
        g2.setColor(Color.YELLOW);
        g2.rotate(Math.toRadians(paddle.getAngle()), (paddle.getX()), (paddle.getY() + paddle.getHeight()));
        //left cannon
        g2.fillRect(paddle.getX() - paddle.getWidth() / 2, paddle.getY() - paddle.getWidth() / 2 + paddle.getHeight(), paddle.getHeight(), paddle.getWidth() / 2);
        //right cannon
        g2.fillRect(paddle.getX() + paddle.getWidth() / 2 - paddle.getHeight(), paddle.getY() - paddle.getWidth() / 2 + paddle.getHeight(), paddle.getHeight(), paddle.getWidth() / 2);

        for (Ball hex : hexBall) {
            if (hex.getDamage() > 0)
                g2.fillOval(hex.getBallposX(), hex.getBallposY(), 16, 16);
        }
        g2.setTransform(old);
    }

    public void moveHex(int time) {
        if (time % 200 == 0) {
            Ball hexBallL = new Ball(16, 16, paddle.getX() - paddle.getWidth() / 2, paddle.getY() - paddle.getWidth() / 2, 0, -2);
            Ball hexBallR = new Ball(16, 16, paddle.getX() + paddle.getWidth() / 2 - paddle.getHeight(), paddle.getY() - paddle.getWidth() / 2, 0, -2);
            hexBallL.setDamage(1);
            hexBallR.setDamage(1);
            hexBall.add(hexBallL);
            hexBall.add(hexBallR);
        }

        for (Ball hex : hexBall) {
            hex.move();
        }
    }


    public int getExpansionAbilityCount() {
        return expansionAbilityCount;
    }

    public void setExpansionAbilityCount(int expansionAbilityCount) {
        this.expansionAbilityCount = expansionAbilityCount;
    }

    public int getHexAbilityCount() {
        return hexAbilityCount;
    }

    public void setHexAbilityCount(int hexAbilityCount) {
        this.hexAbilityCount = hexAbilityCount;
    }

    public int getUnstoppableAbilityCount() {
        return unstoppableAbilityCount;
    }

    public void setUnstoppableAbilityCount(int unstoppableAbilityCount) {
        this.unstoppableAbilityCount = unstoppableAbilityCount;
    }

}
