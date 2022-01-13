package domain.objects.obstacles;

import javax.swing.*;
import java.awt.*;


public class ObstacleSimple extends Obstacle {

    public ObstacleSimple() {
        super();
        setFirmness(1);
        setMovement(Math.random() <= 0.2);
        setName("Wall Maria");
        setColor(Color.WHITE);
        setImage(image());
        setType("simple");
    }

    private JPanel image() {
        JPanel simple = new JPanel();
        simple.setPreferredSize(new Dimension(getWidth(), 20));
        simple.setBackground(getColor());
        simple.setVisible(true);
        return simple;
    }
}
