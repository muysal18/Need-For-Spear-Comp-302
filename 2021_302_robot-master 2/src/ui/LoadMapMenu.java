package ui;

import domain.Controller;
import ui.runningmode.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;

public class LoadMapMenu extends JFrame implements ActionListener {

    HashMap<JButton, String> mapButtons = new HashMap<>();
    Controller controller = Controller.getInstance();
    JButton back;
    int width = 1200;
    int height = 750;

    LoadMapMenu() {
        setTitle("Select the map that you want to play on");
        setPreferredSize(new Dimension(width, height));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        add(mapPanel(controller));

        back = new JButton("Back");
        back.setVisible(true);
        back.addActionListener(this);
        add(back, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    private JPanel mapPanel(Controller controller) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        panel.setSize(new Dimension(200, 200));
        panel.setBackground(Color.black);
        try {
            controller.maps();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (String str : controller.mapIDList) {
            JButton mapID = new JButton(str);
            mapID.setVisible(true);
            mapID.addActionListener(this);
            mapButtons.put(mapID, str);
        }
        gbc.gridy = 0;

        for (JButton button : mapButtons.keySet()) {

            JLabel str = new JLabel("Map ID: ");
            str.setForeground(Color.white);
            gbc.gridx = 0;
            panel.add(str, gbc);
            gbc.gridx++;
            panel.add(button, gbc);
            gbc.gridy++;
        }
        panel.setVisible(true);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == back) {
            goBack();
        }
        if (mapButtons.containsKey(evt.getSource())) {
            String str = mapButtons.get(evt.getSource());
            try {
                controller.loadMap(str);
                dispose();
                new GameFrame();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void goBack() {
        dispose();
        new NewGameMenu();
    }

}
