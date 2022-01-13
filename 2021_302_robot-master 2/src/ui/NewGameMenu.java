package ui;

import ui.buildingmode.BuildingModeFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGameMenu extends JFrame implements ActionListener {

    JPanel buttonPanel = new JPanel();
    JButton newMap;
    JButton loadMap;
    JButton quit;
    int width = 1000;
    int height = 750;

    public NewGameMenu() {

        setSize(width, height);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        newMap = new JButton("New Map");
        newMap.addActionListener(this);
        buttonPanel.add(newMap);

        loadMap = new JButton("Load Map");
        loadMap.addActionListener(this);
        buttonPanel.add(loadMap);

        quit = new JButton("quit");
        quit.addActionListener(this);
        add(quit, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == quit) {
            System.exit(0);
        }
        if (e.getSource() == newMap) {
            createNewMap();
        }
        if (e.getSource() == loadMap) {
            loadMap();
        }
    }

    private void createNewMap() {
        dispose();
        new BuildingModeFrame();
    }

    private void loadMap() {
        dispose();
        new LoadMapMenu();
    }
}
