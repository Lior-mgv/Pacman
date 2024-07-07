package Controller;

import Windows.HighScoresWindow;
import Windows.Menu;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MenuExit extends KeyAdapter {
    private final Window window;

    public MenuExit(Window window) {
        this.window = window;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_Q) {
            new Menu();
            window.dispose();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
