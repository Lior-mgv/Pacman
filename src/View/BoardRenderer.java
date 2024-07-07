package View;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class BoardRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = new JLabel();
        label.setOpaque(true);
        label.setBackground(Color.BLACK);
        Integer intValue = (Integer) value;
        switch (intValue){
            case 2 ->{
                label.setIcon(new ImageIcon("Resources\\Obstacle.png"));
            }
            case 3 ->{
                label.setIcon(new ImageIcon("Resources\\LeftBorder.png"));
            }
            case 4 ->{
                label.setIcon(new ImageIcon("Resources\\RightBorder.png"));
            }
            case 5 ->{
                label.setIcon(new ImageIcon("Resources\\TopBorder.png"));
            }
            case 6 ->{
                label.setIcon(new ImageIcon("Resources\\BottomBorder.png"));
            }
        }
        return label;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
