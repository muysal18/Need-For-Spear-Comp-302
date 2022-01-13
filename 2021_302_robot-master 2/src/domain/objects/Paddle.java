package domain.objects;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Paddle {

    private static Paddle instance;
    private final Rectangle p;
    private final int height = 20;
    private int width = 120;
    private int location_x = 600;
    private int location_y = 450;
    private int angle;
    private int remainingExpansionTime;
    private int remainingHexTime;
    private Point topCenter;
    private Point bottomCenter;
    private Point upperLeft;
    private Point upperRight;
    private Point lowerLeft;
    private Point lowerRight;

    private Paddle() {
        topCenter = new Point(location_x, location_y);
        bottomCenter = new Point(location_x, location_y + 20);
        upperLeft = new Point(location_x - width / 2, location_y);
        upperRight = new Point(location_x + width / 2, location_y);
        lowerLeft = new Point(location_x - width / 2, (location_y + height));
        lowerRight = new Point(location_x + width / 2, (location_y + height));
        p = new Rectangle(upperLeft.x, upperLeft.y, getWidth(), getHeight());
    }

    public static Paddle getInstance() {
        if (instance == null)
            instance = new Paddle();
        return instance;
    }

    public Point getUpperLeft() {
        return upperLeft;
    }

    public Point getUpperRight() {
        return upperRight;
    }

    public Point getLowerLeft() {
        return lowerLeft;
    }

    public Point getLowerRight() {
        return lowerRight;
    }

    private void setPoints() {
        int x = (int) ((getWidth() / 2 * Math.cos(Math.toRadians(angle))) + (0 * Math.sin(Math.toRadians(angle))));
        int y = (int) ((getWidth() / 2 * -Math.sin(Math.toRadians(angle))) + (0 * Math.cos(Math.toRadians(angle))));

        upperRight.setLocation(topCenter.x + x, topCenter.y - y);
        lowerRight.setLocation(bottomCenter.x + x, bottomCenter.y - y);
        upperLeft.setLocation(topCenter.x - x, topCenter.y + y);
        lowerLeft.setLocation(bottomCenter.x - x, bottomCenter.y + y);
    }

    public void drawPaddle(Graphics2D g2) {
        p.x = getX() - (getWidth() / 2);
        p.y = getY();
        p.setSize(getWidth(), getHeight());
        AffineTransform old = g2.getTransform();
        g2.setColor(Color.BLUE);
        g2.rotate(Math.toRadians(getAngle()), (getX()), (getY() + getHeight() / 2));
        g2.fill(p);
        g2.setTransform(old);
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public int getX() {
        return location_x;
    }

    public void setX(int location_x) {
        this.location_x = location_x;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getY() {
        return location_y;
    }

    public void moveRight(int dx) {
        location_x += dx;
        topCenter.setLocation(location_x, location_y);
        bottomCenter.setLocation(location_x, location_y + 20);
        setPoints();
    }

    public void moveLeft(int dx) {
        location_x -= dx;
        topCenter.setLocation(location_x, location_y);
        bottomCenter.setLocation(location_x, location_y + 20);
        setPoints();
    }

    public void rotate(String direction) {
        if (direction.equals("right")) {
            rotateRight();
        } else {
            rotateLeft();
        }
        setPoints();
    }

    private void rotateLeft() {
        if (angle >= -30) {
            angle -= 15;
        } else {
            angle = -45;
        }
    }

    private void rotateRight() {
        if (angle <= 30) {
            angle += 15;
        } else {
            angle = 45;
        }
    }

    public void updateTime(Abilities abilities, int decreaseTime) {
        remainingExpansionTime -= decreaseTime;
        remainingHexTime -= decreaseTime;

        if (remainingExpansionTime < 0 && abilities.expansionActive) {
            abilities.deactivateExpansion(this);
        }

        if (remainingHexTime < 0 && abilities.hexActive) {
            abilities.deactivateHex();
        }
    }

    public void startExpansion(int expansionTime) {
        remainingExpansionTime = expansionTime;
    }

    public void startHex(int hexTime) {
        remainingHexTime = hexTime;
    }

}
