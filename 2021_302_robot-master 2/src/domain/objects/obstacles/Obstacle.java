package domain.objects.obstacles;

import javax.swing.*;
import java.awt.*;

public class Obstacle {

    private String ability;
    private String name;
    private int width = 24;
    private int height = 20;
    private int firmness;
    private boolean movement = false;
    private boolean isExplosive = false;
    private boolean gift = false;
    private boolean falling = false;
    private Color color;
    private JPanel image;
    private Point coordinates;
    private int location;
    private String type;
    private int remainingFrozenTime = 0;
    private Rectangle brick;

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public Rectangle getBrick() {
        return brick;
    }

    public void setBrick(int x, int y, int width, int height) {
        this.brick = new Rectangle(x, y, width, height);
    }

    public void drawObstacle(Graphics2D g2d) {
        g2d.setColor(getColor());
        g2d.fillRect(getCoordinates().x, getCoordinates().y, getWidth(), 20);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int initialSpawnLocation) {
        this.location = initialSpawnLocation;
    }

    public JPanel getImage() {
        return image;
    }

    public void setImage(JPanel image) {
        this.image = image;
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public void startFrozen(int frozenTime) {
        remainingFrozenTime = frozenTime;
    }

    public boolean isFrozen() {
        return remainingFrozenTime > 0;
    }

    public void updateFrozenTime(int decreaseTime) {
        remainingFrozenTime -= decreaseTime;
        if (remainingFrozenTime < 0)
            remainingFrozenTime = 0;
    }

    public Color getColor() {
        if (remainingFrozenTime > 0)  return Color.GREEN;
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGift() {
        return gift;
    }

    public void setGift(boolean gift) {
        this.gift = gift;
    }

    public boolean isMovement() {
        return movement;
    }

    public void setMovement(boolean movement) {
        this.movement = movement;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getFirmness() {
        return firmness;
    }

    public void setFirmness(int firmness) {
        this.firmness = firmness;
    }

    public void decreaseFirmness(int ballDamage) {
        this.firmness -= ballDamage;
    }

    public boolean isExplosive() {
        return isExplosive;
    }

    public void setExplosive(boolean explosive) {
        isExplosive = explosive;
    }

}
