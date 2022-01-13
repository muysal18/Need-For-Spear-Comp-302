package ui.runningmode;


import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    StatPanel stats = StatPanel.getInstance();
    GamePanel game = GamePanel.getInstance();

    public GameFrame() {
        setTitle("Need For Spear");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        add(game, BorderLayout.SOUTH);
        add(stats, BorderLayout.NORTH);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
