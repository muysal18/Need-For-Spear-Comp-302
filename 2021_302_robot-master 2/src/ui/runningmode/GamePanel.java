package ui.runningmode;

import domain.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class GamePanel extends JPanel implements KeyListener, ActionListener {

    private static final int DELAY = 10;
    private static final Controller controller = Controller.getInstance();
    private static GamePanel instance;
    private final Timer timer;
    private boolean play = false;
    private boolean pause = false;

    private GamePanel() {
        addKeyListener(this);
        setBackground(Color.black);
        setFocusable(true);
        setPreferredSize(new Dimension(1200, 500));
        setFocusTraversalKeysEnabled(false);
        setVisible(true);
        timer = new Timer(DELAY, this);
    }

    public static GamePanel getInstance() {
        if (instance == null) instance = new GamePanel();
        return instance;
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        controller.game().drawGame(g2);

        if (!play) {
            g2.setFont(new Font("serif", Font.BOLD, 20));
            g2.drawString("Press W to shoot the ball", 500, 300);
        }

        g2.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (play && !pause) {
            controller.game().tick();

            if (controller.game().dead()) {
                controller.game().restart();
                play = false;
                timer.stop();
                if (controller.game().remainingLives == 0) {
                    setVisible(false);
                    System.out.println("Game Over");
                }
            }
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            if (!timer.isRunning()) {
                timer.start();
                play = true;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_P) {
            if (timer.isRunning()) {
                timer.stop();
                pause = !pause;
                new PauseFrame();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (play && !pause) controller.game().movePaddleRight();
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (play && !pause) controller.game().movePaddleLeft();
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            if (play && !pause) controller.game().rotatePaddleLeft();
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            if (play && !pause) controller.game().rotatePaddleRight();
        }
        if (e.getKeyCode() == KeyEvent.VK_T) {
            if (play && !pause) controller.game().ability("E");
        }
        if (e.getKeyCode() == KeyEvent.VK_H) {
            if (play && !pause) controller.game().ability("H");
        }
        if (e.getKeyCode() == KeyEvent.VK_U) {
            if (play && !pause) controller.game().ability("U");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {


    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void continueGame() {
        timer.start();
        pause = !pause;
    }
}
