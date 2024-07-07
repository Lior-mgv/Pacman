package Windows;


import Controller.MenuExit;
import Model.HighScores;

import javax.swing.*;
import java.awt.*;

public class HighScoresWindow extends JFrame{
    private JList<String> leaderboardList;
    public HighScoresWindow() {
        setTitle("High scores");
        var highScores = HighScores.loadHighScores();
        highScores.sortHighScores();
        DefaultListModel<String> highScoresModel = new DefaultListModel<>();
        int count = 1;
        for (var entry : highScores.getHighScoresList()) {
            highScoresModel.addElement(count + ".  " + entry.toString());
            count++;
        }
        leaderboardList = new JList<>(highScoresModel);
        var renderer = new HighScoresRenderer();
        leaderboardList.setCellRenderer(renderer);
        leaderboardList.setBackground(Color.BLACK);
        leaderboardList.setForeground(Color.WHITE);
        leaderboardList.setFont(new Font("Arial", Font.BOLD, 16));
        JScrollPane scrollPane = new JScrollPane(leaderboardList);
        add(scrollPane, BorderLayout.CENTER);
        setSize(300, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        leaderboardList.addKeyListener(new MenuExit(this));
        setVisible(true);
    }
    private class HighScoresRenderer extends DefaultListCellRenderer{
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if(index == 0) label.setForeground(Color.GREEN);
            else if(index == 1) label.setForeground(Color.YELLOW);
            else if(index == 2) label.setForeground(Color.RED);
            setHorizontalAlignment(CENTER);
            return label;
        }
    }
}
