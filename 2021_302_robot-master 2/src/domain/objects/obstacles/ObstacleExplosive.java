package domain.objects.obstacles;

import javax.swing.*;
import java.awt.*;

public class ObstacleExplosive extends Obstacle {

    public ObstacleExplosive() {
        super();
        setExplosive(true);
        setFirmness(1);
        setMovement(Math.random() <= 0.2);
        setName("Pandora’s Box");
        setColor(Color.MAGENTA);
        setImage(image());
        setType("explosive");
        setWidth(15);
        setHeight(15);
    }

    private JPanel image() {
        JPanel explosive = new JPanel();
        explosive.setPreferredSize(new Dimension(getWidth(), getWidth()));
        explosive.setBackground(getColor());
        explosive.setVisible(true);
        return explosive;
    }

    @Override
    public void drawObstacle(Graphics2D g2d) {
        g2d.setColor(getColor());
        g2d.fillOval(getCoordinates().x + getWidth() / 3, getCoordinates().y + getWidth() / 4, getWidth(), getHeight());
    }
}
