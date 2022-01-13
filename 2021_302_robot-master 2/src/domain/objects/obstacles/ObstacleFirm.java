package domain.objects.obstacles;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ObstacleFirm extends Obstacle {

    public ObstacleFirm() {
        super();
        setFirmness((new Random().nextInt(4)) + 2);
        setMovement(Math.random() <= 0.2);
        setName("Stein's Gate");
        setColor(Color.lightGray);
        setImage(image());
        setType("firm");
    }

    private JPanel image() {
        JPanel firm = new JPanel();
        JLabel l = new JLabel(String.valueOf(getFirmness()));
        firm.setPreferredSize(new Dimension(getWidth(), 20));
        firm.setBackground(getColor());
        firm.add(l);
        firm.setVisible(true);
        return firm;
    }

    @Override
    public void drawObstacle(Graphics2D g2d){
        g2d.setColor(getColor());
        g2d.fillRect(getCoordinates().x, getCoordinates().y, getWidth(), 20);
        g2d.setColor(Color.black);
        g2d.drawString(String.valueOf(getFirmness()), getCoordinates().x + 8, getCoordinates().y + 15);
    }
}