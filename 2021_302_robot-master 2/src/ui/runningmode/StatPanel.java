package ui.runningmode;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class StatPanel extends JPanel {
    private static final StatPanel statPanel = new StatPanel();
    public JLabel lives = new JLabel("Lives: 3");
    public JLabel clock = new JLabel("Time: 0");
    public JLabel score = new JLabel("Score: 0");
    public JLabel unstoppableCount = new JLabel("Unstoppable: 0");
    public JLabel expansionCount = new JLabel("Expansion: 0");
    public JLabel hexCount = new JLabel("Hex: 0");


    private StatPanel() {
        setPreferredSize(new Dimension(1200, 30));
        setBorder(new LineBorder(Color.black));
        lives.setForeground(Color.black);
        lives.setVisible(true);
        add(lives, BorderLayout.WEST);
        clock.setForeground(Color.black);
        clock.setVisible(true);
        add(clock, BorderLayout.WEST);
        score.setForeground(Color.black);
        score.setVisible(true);
        add(score, BorderLayout.WEST);

        expansionCount.setForeground(Color.black);
        expansionCount.setVisible(true);
        add(expansionCount, BorderLayout.EAST);
        unstoppableCount.setForeground(Color.black);
        unstoppableCount.setVisible(true);
        add(unstoppableCount, BorderLayout.EAST);
        hexCount.setForeground(Color.black);
        hexCount.setVisible(true);
        add(hexCount, BorderLayout.EAST);
        setVisible(true);

    }

    public static StatPanel getInstance() {
        return statPanel;
    }

}
