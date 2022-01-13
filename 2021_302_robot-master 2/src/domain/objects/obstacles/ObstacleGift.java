package domain.objects.obstacles;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ObstacleGift extends Obstacle {

    Random random = new Random();

    public ObstacleGift() {
        super();
        setGift(true);
        setFirmness(1);
        setMovement(Math.random() <= 0.2); //hareket var mı sor
        setName("Gift of Uranus");
        setColor(Color.CYAN);
        setImage(image());
        setType("gift");
        gift();
    }

    private void gift() {
        switch (random.nextInt(4)) {
            case 0 -> setAbility("C");
            case 1 -> setAbility("E");
            case 2 -> setAbility("U");
            case 3 -> setAbility("H");
        }
    }

    private JPanel image() {
        JPanel gift = new JPanel();
        gift.setPreferredSize(new Dimension(getWidth(), 20));
        gift.setBackground(getColor());
        gift.setVisible(true);
        return gift;
    }

    @Override
    public void drawObstacle(Graphics2D g2d) {
        super.drawObstacle(g2d);
        if (isFalling()) {
            g2d.setColor(Color.black);
            g2d.drawString(getAbility(), getCoordinates().x + 8, getCoordinates().y + 15);
        }
    }
}
