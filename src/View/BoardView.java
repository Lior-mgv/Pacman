package View;

import Settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BoardView extends JLabel {
    private JTable board = new JTable();
    private Board boardModel;
    private Image image;

    public BoardView(Board boardModel) {
        this.boardModel = boardModel;
        InitTable();
        image = tableToImage();
        setIcon(new ImageIcon(image));
    }

    private Image tableToImage() {
        BufferedImage image = new BufferedImage(board.getWidth(), board.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        board.print(g2d);
        g2d.dispose();
        return image;
    }

    private void InitTable() {
        board = new JTable(boardModel);
        for (int i = 0; i < board.getColumnCount(); i++) {
            board.getColumnModel().getColumn(i).setCellRenderer(new BoardRenderer());
        }
        setColumnsWidth(board, Settings.cellSize);
        board.setRowHeight(Settings.cellSize);
        board.setIntercellSpacing(new Dimension(0, 0));
        board.setSize(board.getColumnCount() * Settings.cellSize, board.getRowCount() * Settings.cellSize);
        board.setLocation(0, 0);
        board.setFocusable(false);
        board.setRowSelectionAllowed(false);
        board.setCellSelectionEnabled(false);
        board.setColumnSelectionAllowed(false);
    }

    private void setColumnsWidth(JTable board, int width) {
        for (int i = 0; i < board.getColumnCount(); i++) {
            board.getColumnModel().getColumn(i).setWidth(width);
        }
    }

    public void scaleImage(double scale) {
        var scalingHelper = new ScalingHelper(scale);
        setIcon(new ImageIcon(scalingHelper.scaleImage(image, getSize())));
    }
}
