package ui.buildingmode;

import domain.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class EditingAreaPanel extends JPanel implements ActionListener {
    private static EditingAreaPanel instance;
    public ArrayList<JPanel> gridList = new ArrayList<>();
    int row = 10;
    int column = 40;
    int width = 1200;
    int height = 500;
    JPanel create = new JPanel();
    JPanel edit = new JPanel();
    JLabel numberOfSimpleLabel;
    JLabel numberOfFirmLabel;
    JLabel numberOfExplosiveLabel;
    JLabel numberOfGiftLabel;
    JLabel numberOfTotalLabel;
    JLabel minNumSimpleLabel;
    JLabel minNumFirmLabel;
    JLabel minNumExplosiveLabel;
    JLabel minNumGiftLabel;
    JTextField numberOfSimpleField;
    JTextField numberOfFirmField;
    JTextField numberOfExplosiveField;
    JTextField numberOfGiftField;
    JButton generateButton;
    JButton changeObstacleLoc;
    int minNumOfSimple = 75;
    int minNumOfFirm = 10;
    int minNumOfExplosive = 5;
    int minNumOfGift = 10;
    int simpleNum;
    int firmNum;
    int explosiveNum;
    int giftNum;
    JLabel numberOfTotalField;
    Controller controller;

    private EditingAreaPanel() {

        this.controller = Controller.getInstance();
        JSplitPane splitPane = new JSplitPane();
        splitPane.setDividerSize(0);
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(EditPanel(controller));
        splitPane.setBottomComponent(CreationPanel(controller));
        add(splitPane);
    }

    public static EditingAreaPanel getInstance() {
        if (instance == null)
            instance = new EditingAreaPanel();
        return instance;
    }

    public JPanel CreationPanel(Controller controller) {
        create.setPreferredSize(new Dimension(controller.screenWidth, 100));
        JPanel obstaclePanel = new JPanel(new GridBagLayout());

        //2.column
        numberOfSimpleLabel = new JLabel("Number of Simple Obstacles");
        numberOfFirmLabel = new JLabel("Number of Firm Obstacles");
        numberOfExplosiveLabel = new JLabel("Number of Explosive Obstacles");
        numberOfGiftLabel = new JLabel("Number of Gift Obstacles");
        numberOfTotalLabel = new JLabel("Total number of obstacles : ");

        //3.column
        minNumSimpleLabel = new JLabel(minNumOfSimple + "+");
        minNumFirmLabel = new JLabel(minNumOfFirm + "+");
        minNumExplosiveLabel = new JLabel(minNumOfExplosive + "+");
        minNumGiftLabel = new JLabel(minNumOfGift + "+");

        //4.column
        numberOfSimpleField = new JTextField(3);
        numberOfFirmField = new JTextField(3);
        numberOfExplosiveField = new JTextField(3);
        numberOfGiftField = new JTextField(3);
        numberOfTotalField = new JLabel(totalNumber());


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 2, 2, 2);

        gbc.gridx = 0;
        gbc.gridy = 0;
        obstaclePanel.add(controller.addNewObstacle("simple").getImage(), gbc);
        gbc.gridx = 1;
        obstaclePanel.add(numberOfSimpleLabel, gbc);
        gbc.gridx = 2;
        obstaclePanel.add(minNumSimpleLabel, gbc);
        gbc.gridx = 3;
        obstaclePanel.add(numberOfSimpleField, gbc);

        gbc.gridx = 4;
        JPanel firmObstacleImage = controller.addNewObstacle("firm").getImage();
        firmObstacleImage.removeAll();
        obstaclePanel.add(firmObstacleImage, gbc);
        gbc.gridx = 5;
        obstaclePanel.add(numberOfFirmLabel, gbc);
        gbc.gridx = 6;
        obstaclePanel.add(minNumFirmLabel, gbc);
        gbc.gridx = 7;
        obstaclePanel.add(numberOfFirmField, gbc);

        gbc.gridx = 8;
        obstaclePanel.add(controller.addNewObstacle("explosive").getImage(), gbc);
        gbc.gridx = 9;
        obstaclePanel.add(numberOfExplosiveLabel, gbc);
        gbc.gridx = 10;
        obstaclePanel.add(minNumExplosiveLabel, gbc);
        gbc.gridx = 11;
        obstaclePanel.add(numberOfExplosiveField, gbc);

        gbc.gridx = 12;
        obstaclePanel.add(controller.addNewObstacle("gift").getImage(), gbc);
        gbc.gridx = 13;
        obstaclePanel.add(numberOfGiftLabel, gbc);
        gbc.gridx = 14;
        obstaclePanel.add(minNumGiftLabel, gbc);
        gbc.gridx = 15;
        obstaclePanel.add(numberOfGiftField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        obstaclePanel.add(numberOfTotalLabel, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        obstaclePanel.add(numberOfTotalField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        generateButton = new JButton("Generate Obstacles");
        generateButton.addActionListener(this);
        generateButton.setVisible(true);
        obstaclePanel.add(generateButton, gbc);

        gbc.gridx = 5;
        gbc.gridy = 3;
        changeObstacleLoc = new JButton("Change Obstacle Location");
        changeObstacleLoc.addActionListener(this);
        changeObstacleLoc.setVisible(true);
        obstaclePanel.add(changeObstacleLoc, gbc);

        obstaclePanel.setVisible(true);
        create.add(obstaclePanel);
        create.setVisible(true);
        return create;
    }

    public JPanel EditPanel(Controller controller) {
        edit.setPreferredSize(new Dimension(width, height));
        edit.setLayout(new GridLayout(row * 2, column));
        griding(edit);
        controller.initializeSpawnObstacle(gridList);
        edit.setVisible(true);
        return edit;
    }

    private void griding(JPanel panel) {

        for (int k = 0; k < row * column; k++) {
            JPanel n = new JPanel();
            n.setBorder(BorderFactory.createLineBorder(Color.black, 1));
            n.setBackground(Color.BLACK);
            n.setVisible(true);
            gridList.add(n);
            panel.add(n);
        }
        for (int k = 0; k < row * column; k++) {
            JPanel n = new JPanel();
            n.setBackground(Color.BLACK);
            n.setVisible(true);
            gridList.add(n);
            panel.add(n);
        }
    }

    private void spawnObstacle(Controller controller, String obstacleType, int obstacleNumber) {
        controller.newObstacles(obstacleType, obstacleNumber);
        int obstacleID;
        for (int i = 1; i <= obstacleNumber; i++) {
            obstacleID = controller.spawnLocation.get(controller.spawnLocation.size() - i);
            gridList.get(obstacleID).add(controller.obstacles.get(obstacleID).getImage());
        }
    }

    public void changePosition(Controller controller, int newPosition, int oldPosition) {
        gridList.get(oldPosition).removeAll();
        controller.changeLocation(oldPosition, newPosition);
        gridList.get(newPosition).add(controller.obstacles.get(newPosition).getImage());
        updateUI();
    }

    private String totalNumber() {
        int total = minNumOfSimple + minNumOfFirm + minNumOfGift + minNumOfExplosive + simpleNum + firmNum + explosiveNum + giftNum;
        return String.valueOf(total);
    }

    private void update(Controller controller) {
        if (!Objects.equals(numberOfSimpleField.getText(), "")) {
            if (simpleNum != Integer.parseInt(numberOfSimpleField.getText())) {
                minNumOfSimple += simpleNum;
                simpleNum = Integer.parseInt(numberOfSimpleField.getText());
                spawnObstacle(controller, "simple", simpleNum);
            }
        }
        if (!Objects.equals(numberOfFirmField.getText(), "")) {
            if (firmNum != Integer.parseInt(numberOfFirmField.getText())) {
                minNumOfFirm += firmNum;
                firmNum = Integer.parseInt(numberOfFirmField.getText());
                spawnObstacle(controller, "firm", firmNum);
            }
        }
        if (!Objects.equals(numberOfExplosiveField.getText(), "")) {
            if (explosiveNum != Integer.parseInt(numberOfExplosiveField.getText())) {
                minNumOfExplosive += explosiveNum;
                explosiveNum = Integer.parseInt(numberOfExplosiveField.getText());
                spawnObstacle(controller, "explosive", explosiveNum);
            }
        }
        if (!Objects.equals(numberOfGiftField.getText(), "")) {
            if (giftNum != Integer.parseInt(numberOfGiftField.getText())) {
                minNumOfGift += giftNum;
                giftNum = Integer.parseInt(numberOfGiftField.getText());
                spawnObstacle(controller, "gift", giftNum);
            }
        }
    }

    //kontrol etmek için
    private void print() {
        System.out.println("simple count: " + minNumOfSimple + "+" + simpleNum + "=" + (simpleNum + minNumOfSimple));
        System.out.println("firm count: " + minNumOfFirm + "+" + firmNum + "=" + (firmNum + minNumOfFirm));
        System.out.println("explosive count: " + minNumOfExplosive + "+" + explosiveNum + "=" + (explosiveNum + minNumOfExplosive));
        System.out.println("gift count: " + minNumOfGift + "+" + giftNum + "=" + (giftNum + minNumOfGift));
        System.out.println();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == generateButton) {
            if (Integer.parseInt(totalNumber()) < 400) {
                update(controller);
                print();
                numberOfTotalField.setText(totalNumber());
                this.updateUI();
            }
        }
        if (e.getSource() == changeObstacleLoc) {
            edit.grabFocus();
        }
    }
}
