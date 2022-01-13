package domain;

import domain.objects.Abilities;
import domain.objects.Ball;
import domain.objects.Paddle;
import domain.objects.Ymir;
import domain.objects.obstacles.Obstacle;
import ui.runningmode.StatPanel;

import java.awt.*;
import java.util.ArrayList;


public class Game {

    private static final int DELAY = 10;
    private static Game instance;
    private final int L = 1200;
    private final Ymir ymir = Ymir.getInstance(30000);
    private final ArrayList<Obstacle> removeList = new ArrayList<>();
    private final ArrayList<Obstacle> fallList = new ArrayList<>();
    private final ArrayList<Obstacle> obstacleList = new ArrayList<>();
    private final Paddle paddle = Paddle.getInstance();
    private final Abilities abilities = new Abilities();
    private final Color purple = new Color(100, 50, 200);
    public int remainingLives = 3;
    public int clock = 0;
    private Ball mainBall;
    private int score = 0;
    private boolean exploded = false;

    private Game(ArrayList<Obstacle> list, Ball ball) {
        startGame(ball);
        initializeBricks(list);
    }

    public static Game getInstance(ArrayList<Obstacle> list, Ball ball) {
        if (instance == null) instance = new Game(list, ball);
        return instance;
    }

    public void tick() {
        clock++;
        setClock(clock / 100);
        mainBall.updateTime(abilities, DELAY);
        paddle.updateTime(abilities, DELAY);
        ymir.updateRemainingTime(DELAY, mainBall, obstacleList, fallList);

        collisions();
        mainBall.move();
        if (abilities.hexActive) abilities.moveHex(clock);

        if (!fallList.isEmpty()) {
            for (Obstacle o : fallList) {
                o.setCoordinates(new Point(o.getCoordinates().x, o.getCoordinates().y + 1));
                if (o.getCoordinates().y > 470) removeList.add(o);
            }
            fallList.removeAll(removeList);
        }
    }

    public void startGame(Ball ball) {
        this.paddle.setAngle(paddle.getAngle());
        this.paddle.setX(paddle.getX());
        mainBall = ball;
        mainBall.setDamage(1);
        mainBall.setDamage(ball.getDamage());
    }

    public void resetPositions() {
        paddle.setAngle(0);
        paddle.setX(L / 2);
        mainBall = new Ball(16, 16, paddle.getX() - 8, paddle.getY() - 16, -1, -2);
        mainBall.setDamage(1);
    }

    public void initializeBricks(ArrayList<Obstacle> list) {
        for (Obstacle o : list) {
            o.setBrick(o.getCoordinates().x, o.getCoordinates().y, o.getWidth(), o.getHeight());
            obstacleList.add(o);
        }
    }

    public void drawGame(Graphics2D g2) {
        for (Obstacle o : obstacleList) {
            o.updateFrozenTime(DELAY);
            o.drawObstacle(g2);
        }

        mainBall.drawBall(g2);
        paddle.drawPaddle(g2);
        if (abilities.hexActive) abilities.drawHex(g2);
    }

    public boolean dead() {
        if (mainBall.getBallposY() > 470) return true;
        else return exploded;
    }

    public void restart() {
        abilities.deactivateExpansion(paddle);
        abilities.deactivateHex();
        exploded = false;
        obstacleList.removeAll(fallList);
        fallList.clear();
        remainingLives--;
        setLives(remainingLives);
        resetPositions();
    }

    public void ballWallPaddleCollision() {
        if (paddleIntersection(mainBall.getBallRect())) mainBall.reverseDirY();
        if (mainBall.getBallposX() < 0) mainBall.reverseDirX();
        if (mainBall.getBallposY() < 0) mainBall.reverseDirY();
        if (mainBall.getBallposX() > L - 20) mainBall.reverseDirX();
    }

    public void ballBrickCollision(Obstacle obstacle) {
        if (mainBall.getBallRect().intersects(obstacle.getBrick()) && !obstacle.isFalling()) {
            if (!obstacle.isFrozen()) {
                if (!abilities.unstoppableActive) bounce(mainBall, obstacle);
                hit(obstacle, mainBall.getDamage());
            } else {
                bounce(mainBall, obstacle);
                if (abilities.unstoppableActive) hit(obstacle, 1);
            }
        }
    }

    public void hexBrickCollision(Obstacle obstacle, ArrayList<Ball> hexBall) {
        for (Ball hex : hexBall) {
            if (hex.getBallRect().intersects(obstacle.getBrick()) && !obstacle.isFalling() && !obstacle.isFrozen()) {
                hit(obstacle, hex.getDamage());
                hex.setDamage(0);
            }
        }
    }

    private void hit(Obstacle obstacle, int damage) {
        obstacle.decreaseFirmness(damage);
        if (clock > 100 && obstacle.getColor() != purple) {
            score += (300 / (clock / 100));
            setScore(score);
        }

        if ((obstacle.isExplosive() || obstacle.isGift()) && !obstacle.isFalling()) {
            obstacle.setFalling(true);
            fallList.add(obstacle);
        } else if (obstacle.getFirmness() <= 0) removeList.add(obstacle);
    }

    private void bounce(Ball mainBall, Obstacle obstacle) {
        if (mainBall.getBallposX() + 15 <= obstacle.getBrick().x || mainBall.getBallposX() + 1 >= obstacle.getBrick().x + obstacle.getWidth()) {
            mainBall.reverseDirX();
        } else mainBall.reverseDirY();
    }

    private void brickPaddleCollision(Obstacle obstacle) {
        if (paddleIntersection(obstacle.getBrick())) {
            if (obstacle.isExplosive()) exploded = true;
            else if (obstacle.isGift()) {
                giveGift(obstacle);
                removeList.add(obstacle);
            }
        }
    }

    private void collisions() {
        ballWallPaddleCollision();
        for (Obstacle obstacle : obstacleList) {
            brickPaddleCollision(obstacle);
            ballBrickCollision(obstacle);
            hexBrickCollision(obstacle, abilities.hexBall);
            obstacle.setBrick(obstacle.getCoordinates().x, obstacle.getCoordinates().y, obstacle.getWidth(), obstacle.getHeight());
        }
        obstacleList.removeAll(removeList);
    }

    private void giveGift(Obstacle obstacle) {
        switch (obstacle.getAbility()) {
            case "C" -> {
                remainingLives++;
                setLives(remainingLives);
            }
            case "E" -> {
                abilities.setExpansionAbilityCount(abilities.getExpansionAbilityCount() + 1);
                setAbilityCount("E");
            }
            case "U" -> {
                abilities.setUnstoppableAbilityCount(abilities.getUnstoppableAbilityCount() + 1);
                setAbilityCount("U");
            }
            case "H" -> {
                abilities.setHexAbilityCount(abilities.getHexAbilityCount() + 1);
                setAbilityCount("H");
            }
        }
    }

    private boolean paddleIntersection(Rectangle rectangle) {
        return (rectangle.intersectsLine(paddle.getUpperLeft().x, paddle.getUpperLeft().y, paddle.getUpperRight().x, paddle.getUpperRight().y)) //üst taraf
                ||
                (rectangle.intersectsLine(paddle.getUpperLeft().x, paddle.getUpperLeft().y, paddle.getLowerLeft().x, paddle.getLowerLeft().y)) //sol taraf
                ||
                (rectangle.intersectsLine(paddle.getUpperRight().x, paddle.getUpperRight().y, paddle.getLowerRight().x, paddle.getLowerRight().y)); //sağ taraf
    }

    public void ability(String str) {
        switch (str) {
            case "U" -> abilities.activateUnstoppableEnchantedSphere(mainBall);
            case "E" -> abilities.activateExpansion();
            case "H" -> abilities.activateHex();
        }
        setAbilityCount(str);
    }

    public void movePaddleRight() {
        if (paddle.getX() >= L - (paddle.getWidth()) / 2) {
            paddle.setX(L - (paddle.getWidth() / 2));
        } else {
            paddle.moveRight(L / 60);
        }
    }

    public void movePaddleLeft() {
        if (paddle.getX() <= (paddle.getWidth()) / 2) {
            paddle.setX(paddle.getWidth() / 2);
        } else {
            paddle.moveLeft(L / 60);
        }
    }

    public void rotatePaddleRight() {
        paddle.rotate("right");
    }

    public void rotatePaddleLeft() {
        paddle.rotate("left");
    }

    public void setLives(int remaining) {
        StatPanel.getInstance().lives.setText("Lives: " + remaining);
    }

    public void setClock(int time) {
        StatPanel.getInstance().clock.setText("Time: " + time);
    }

    public void setScore(int score) {
        StatPanel.getInstance().score.setText("Score: " + score);
    }

    public void setAbilityCount(String str) {
        switch (str) {
            case "E" -> StatPanel.getInstance().expansionCount.setText("Expansion: " + abilities.getExpansionAbilityCount());
            case "H" -> StatPanel.getInstance().hexCount.setText("Hex: " + abilities.getHexAbilityCount());
            case "U" -> StatPanel.getInstance().unstoppableCount.setText("Unstoppable: " + abilities.getUnstoppableAbilityCount());
        }
    }
}
