package ui.runningmode;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PauseFrame extends JFrame implements ActionListener {
    GamePanel game = GamePanel.getInstance();
    int width = 200;
    int height = 300;
    JPanel buttonsPanel = new JPanel();
    JButton quit;
    JButton continueGame;
    JButton help;


    public PauseFrame() {

        // Configure the JFrame
        setSize(width, height);
        setResizable(false);
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        setButtonsPanel();
        add(buttonsPanel, BorderLayout.CENTER);

        // Create the quit button
        quit = new JButton("Quit Game");
        quit.addActionListener(this);
        add(quit, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void setButtonsPanel() {
        //Create Continue Game Button
        continueGame = new JButton("Continue");
        continueGame.addActionListener(this);
        buttonsPanel.add(continueGame);

        // Create the Help button
        help = new JButton("    Help    ");
        help.addActionListener(this);
        buttonsPanel.add(help);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == continueGame) {
            game.continueGame();
            dispose();
        }
        if (e.getSource() == quit) {
            System.exit(0);
        }
    }
}
