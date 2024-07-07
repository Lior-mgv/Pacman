package Windows;

import Controller.MenuExit;

import javax.swing.*;
import java.awt.*;

public class GameStartDialog extends JDialog {
    private final JTextField height;
    private final JTextField nickname;
    private final JTextField width;

    public GameStartDialog() {
        setLayout(new GridBagLayout());
        setSize(500,200);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0,15,15,15);

        add(new JLabel("Enter your nickname: "), gbc);
        gbc.gridy++;
        add(new JLabel("Enter board height: "), gbc);
        gbc.gridy++;
        add(new JLabel("Enter board width: "), gbc);
        gbc.gridy++;
        var back = new JButton("Back");
        back.setFocusable(false);
        back.addActionListener(e -> {
            new Menu();
            this.dispose();
        });
        add(back, gbc);

        gbc.gridx++;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nickname = new JTextField();
        add(nickname, gbc);
        gbc.gridy++;
        height = new JTextField("20");
        add(height, gbc);
        gbc.gridy++;
        width = new JTextField("20");
        add(width, gbc);
        gbc.gridy++;
        var save = new JButton("Ok");
        save.setFocusable(false);
        save.addActionListener(e -> {
            String error = checkErrors();
            if(error != null){
              JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
              return;
            }
            else {
                new GameWindow(nickname.getText(), Integer.parseInt(width.getText()), Integer.parseInt(height.getText()));
                this.dispose();
            }
        });
        add(save, gbc);
        setVisible(true);
        setLocationRelativeTo(null);
    }
    private String checkErrors(){
        if(nickname.getText().length() == 0 || width.getText().length() == 0 || height.getText().length() == 0){
            return "All fields should be filled.";
        }
        int heightVal = Integer.parseInt(height.getText());
        int widthVal = Integer.parseInt(width.getText());
        if(heightVal > 100 || heightVal < 0 || widthVal > 100 || widthVal < 0){
            return "Board dimensions should be from 10x10 to 100x100";
        }
        return null;
    }
}
