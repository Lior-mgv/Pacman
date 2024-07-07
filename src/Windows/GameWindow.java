package Windows;

import Controller.*;
import Model.*;
import Model.Upgrades.HunterUpgrade;
import Model.Upgrades.ImmunityUpgrade;
import Model.Upgrades.SpeedUpgrade;
import Settings.Settings;
import View.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameWindow extends JFrame {
    private JLabel score;
    private GameModel model;
    private Controller controller;
    private ArrayList<JLabel> lives;
    private GridBagConstraints scoreConstraints;
    private GridBagConstraints messageConstraints;
    private JPanel bottomPanel;
    private JPanel mainPanel;
    private String nickname;
    private JLabel timer;
    private JLabel upgrade;
    private JLabel upgradeMessage;
    private int boardWidth;
    private int boardHeight;

    public GameWindow(String nickname, int width, int height) {
        this.nickname = nickname;
        boardWidth = width;
        boardHeight = height;

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        var mazeGen = new MazeGenerator(height, width);
        Board board = new Board(mazeGen.generate());
        model = new GameModel(board.getModel());
        GameView view = new GameView(board, model);
        view.setFocusable(true);
        view.requestFocusInWindow();
        controller = new Controller(model, view, this);
        view.addKeyListener(controller);
        view.setPreferredSize(new Dimension(board.getColumnCount() * Settings.cellSize,
                board.getRowCount() * Settings.cellSize));
        mainPanel.add(view, BorderLayout.CENTER);

        var topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension((int)(view.getPreferredSize().getWidth()),50));
        topPanel.setBackground(Color.BLACK);
        scoreConstraints = new GridBagConstraints();
        scoreConstraints.gridx = 0;
        scoreConstraints.gridy = 0;
        scoreConstraints.insets = new Insets(0,0,0,0);
        timer = new JLabel();
        timer.setFont(new Font("Arial", Font.BOLD, 30));
        timer.setForeground(Color.WHITE);
        topPanel.add(timer, scoreConstraints);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        bottomPanel = new JPanel(new GridBagLayout());
        lives = new ArrayList<>();
        scoreConstraints.anchor = GridBagConstraints.WEST;
        scoreConstraints.fill = GridBagConstraints.HORIZONTAL;
        scoreConstraints.insets = new Insets(0,0,0,70);
        scoreConstraints.weightx = 1.0;
        bottomPanel.setBackground(Color.BLACK);
        score = new JLabel();
        score.setFont(new Font("Arial", Font.BOLD, 24));
        score.setForeground(Color.WHITE);
        score.setHorizontalAlignment(SwingConstants.RIGHT);
        bottomPanel.add(score, scoreConstraints);
        bottomPanel.setPreferredSize(new Dimension((int)(view.getPreferredSize().getWidth()),150));

        upgrade = new JLabel();
        GridBagConstraints upgradeConstraints = new GridBagConstraints();
        upgradeConstraints.gridx = 0;
        upgradeConstraints.gridy = 1;
        upgradeConstraints.anchor = GridBagConstraints.WEST;
        upgradeConstraints.insets = new Insets(20, 20, 0, 0);
        bottomPanel.add(upgrade, upgradeConstraints);
        upgradeMessage = new JLabel();
        messageConstraints = new GridBagConstraints();
        messageConstraints.gridx = scoreConstraints.gridx;
        messageConstraints.gridy = 1;
        messageConstraints.anchor = GridBagConstraints.EAST;
        messageConstraints.insets = new Insets(20, 0, 0, 70);
        upgradeMessage.setFont(new Font("Arial", Font.BOLD, 20));
        bottomPanel.add(upgradeMessage, messageConstraints);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel);

        pack();
        setLocationRelativeTo(null);
        setTitle("Pacman");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        controller.start();
    }

    public void setScore(String score) {
        this.score.setText(score);
    }

    public void updateScore() {
        remove(score);
        remove(upgradeMessage);
        setScore("Score:" + model.getScore());
        bottomPanel.add(score, scoreConstraints);
        messageConstraints.gridx = scoreConstraints.gridx;
        bottomPanel.add(upgradeMessage, messageConstraints);
    }
    public void updateTimer(){
        var millisPassed = controller.getTotalPassed().toMillis();
        var minutesPassed = millisPassed/60000;
        var secondsPassed = (millisPassed - 60000 * minutesPassed)/1000;
        timer.setText((minutesPassed < 10 ? "0" : "") + minutesPassed + ":" + (secondsPassed < 10 ? "0" : "")
                + secondsPassed);
    }

    public void updateLives() {
        while(model.getHp() > lives.size()){
            addHp();
        }
        while(model.getHp() < lives.size()){
            bottomPanel.remove(lives.get(lives.size()-1));
            lives.remove(lives.get(lives.size()-1));
        }
        bottomPanel.revalidate();
    }
    public void gameEnd(boolean victory){
        var defeatDialog = new JDialog();
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel();
        if(victory){
            label.setText("CONGRATULATIONS!");
            label.setForeground(Color.GREEN);
        }
        else{
            label.setText("DEFEAT!");
            label.setForeground(Color.RED);
        }
        label.setFont(label.getFont().deriveFont(Font.BOLD, 24f));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JButton backButton = new JButton("Back");
        backButton.setFocusable(false);
        JButton playAgainButton = new JButton("Play again");
        playAgainButton.setFocusable(false);
        backButton.addActionListener(e -> {
            dispose();
            defeatDialog.dispose();
            new Menu();
        });

        playAgainButton.addActionListener(e -> {
            new GameWindow(nickname, boardWidth, boardHeight);
            dispose();
            defeatDialog.dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(backButton);
        buttonPanel.add(playAgainButton);

        panel.add(label, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        defeatDialog.add(panel);

        defeatDialog.setSize(300,150);
        defeatDialog.setLocationRelativeTo(null);
        defeatDialog.setVisible(true);
    }

    private void addHp() {
        var hp = new JLabel();
        hp.setIcon(new ImageIcon("Resources/Pacman2.png"));
        lives.add(hp);
        GridBagConstraints hpConstraints = new GridBagConstraints();
        hpConstraints.anchor = GridBagConstraints.WEST;
        hpConstraints.gridx = scoreConstraints.gridx;
        hpConstraints.gridy = scoreConstraints.gridy;
        hpConstraints.fill = GridBagConstraints.HORIZONTAL;
        hpConstraints.insets = new Insets(0, 20, 0, 0);
        bottomPanel.add(hp, hpConstraints);
        scoreConstraints.gridx++;
    }

    public String getNickname() {
        return nickname;
    }

    public void updateUpgrade() {
        var active = model.getActiveUpgrade();
        if(active != null){
            if(active instanceof SpeedUpgrade) {
                upgrade.setIcon(new ImageIcon("Resources/SpeedUpgrade.png"));
                upgradeMessage.setText("SPEED INCREASED!");
                upgradeMessage.setForeground(Color.BLUE);
            }
            else if(active instanceof ImmunityUpgrade) {
                upgrade.setIcon(new ImageIcon("Resources/ImmunityUpgrade.png"));
                upgradeMessage.setText("YOU ARE INVULNERABLE!");
                upgradeMessage.setForeground(Color.MAGENTA);
            }
            else if(active instanceof HunterUpgrade){
                upgrade.setIcon(new ImageIcon("Resources/HunterPill.png"));
                upgradeMessage.setText("HUNTER MODE!");
                upgradeMessage.setForeground(Color.RED);
            }
        }
        else {
            upgrade.setIcon(null);
            upgradeMessage.setText("");
            upgradeMessage.setForeground(null);
        }
        repaint();
    }
}
