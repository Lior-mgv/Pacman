package Windows;

import javax.swing.*;
import java.awt.*;


public class Menu extends JFrame {
    public Menu() {
        JButton newGame = new JButton("NEW GAME");
        newGame.setFocusable(false);
        newGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGame.addActionListener(e -> {
            new GameStartDialog();
            this.dispose();
        });
        JButton highScores = new JButton("HIGH SCORES");
        highScores.setFocusable(false);
        highScores.setAlignmentX(Component.CENTER_ALIGNMENT);
        highScores.addActionListener(e -> {
            new HighScoresWindow();
            this.dispose();
        });
        JButton exit = new JButton("EXIT");
        exit.setFocusable(false);
        exit.setAlignmentX(Component.CENTER_ALIGNMENT);
        exit.addActionListener(e -> System.exit(0));
        JPanel panel = new JPanel(new GridLayout(3, 1, 0, 15));
        panel.setPreferredSize(new Dimension(250, 250));
        panel.setMaximumSize(new Dimension(250, 250));
        panel.setMinimumSize(new Dimension(250, 250));

        panel.setBackground(Color.BLACK);
        panel.add(newGame);
        panel.add(highScores);
        panel.add(exit);
        Box box = new Box(BoxLayout.Y_AXIS);
        JLabel logo = new JLabel();
        logo.setIcon(new ImageIcon("Resources/pac-man-logo.png"));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        box.add(Box.createVerticalGlue());
        box.add(Box.createHorizontalGlue());
        box.add(logo);
        box.add(Box.createVerticalGlue());
        box.add(panel);
        box.add(Box.createVerticalGlue());
        this.add(box);
        this.getContentPane().setBackground(Color.BLACK);
        this.setSize(600, 700);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(this.getMinimumSize());
        setLocationRelativeTo(null);
        setTitle("Pacman");
        this.setVisible(true);
    }
}
