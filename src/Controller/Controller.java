package Controller;

import Model.*;
import Settings.Settings;
import View.GameView;
import Windows.GameWindow;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.time.Duration;
import java.time.LocalTime;

public class Controller extends Thread implements KeyListener, Serializable {
    private GameWindow gameWindow;
    private GameModel gameModel;
    private GameView gameView;
    private LocalTime iterationStarted;
    private Duration passed;
    private LocalTime started;
    private Duration totalPassed;
    private boolean exited = false;

    public Controller(GameModel gameModel, GameView gameView, GameWindow gameWindow) {
        this.gameModel = gameModel;
        this.gameView = gameView;
        this.gameWindow = gameWindow;
    }

    public void run() {
        started = LocalTime.now();
        while (true) {
            if(exited) return;
            if(gameModel.victory()){
                var highScores = HighScores.loadHighScores();
                highScores.addEntry(new HighScoresEntry(gameWindow.getNickname(), gameModel.getScore()));
                highScores.saveHighScores();
                gameWindow.gameEnd(true);
                return;
            }
            if (gameModel.getPacman().isAlive()) {
                iterationStarted = LocalTime.now();
                totalPassed = Duration.between(started, LocalTime.now());
                gameModel.updateModel();
                gameView.updateView();
                gameWindow.updateLives();
                gameWindow.updateTimer();
                gameWindow.updateScore();
                gameWindow.updateTimer();
                gameWindow.updateUpgrade();
                passed = Duration.between(iterationStarted, LocalTime.now());
                int millisToSleep = (int) (1000 * Settings.timeCoefficient - passed.toMillis());
                if (millisToSleep > 0) {
                    try {
                        Thread.sleep(millisToSleep);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                var highScores = HighScores.loadHighScores();
                highScores.addEntry(new HighScoresEntry(gameWindow.getNickname(), gameModel.getScore()));
                highScores.saveHighScores();
                gameWindow.gameEnd(false);
                return;
            }
        }
    }

    public Duration getTotalPassed() {
        return totalPassed;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        new MenuExit(gameWindow).keyPressed(e);
        if(!gameWindow.isDisplayable()) {
            exited = true;
            return;
        }
        var keyCode = e.getKeyCode();
        switch (keyCode) {
            case 37 -> {
                gameModel.setPacmanDirection(Direction.LEFT);
            }
            case 38 -> {
                gameModel.setPacmanDirection(Direction.UP);
            }
            case 39 -> {
                gameModel.setPacmanDirection(Direction.RIGHT);
            }
            case 40 -> {
                gameModel.setPacmanDirection(Direction.DOWN);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}