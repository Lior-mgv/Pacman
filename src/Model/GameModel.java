package Model;

import Model.Upgrades.*;
import Settings.Settings;

import java.awt.*;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

public class GameModel {
    private int[][] board;
    private LocalTime lastUpdate;
    private LocalTime lastUpgradeCreation;
    private TemporaryUpgrade activeUpgrade = null;
    private ArrayList<FoodObject> foodObjects;
    private Pacman pacman;
    private ArrayList<Ghost> ghosts;
    private ArrayList<Upgrade> upgrades;
    private int score;
    private ArrayList<GameObject> obstacles;

    public GameModel(int[][] boardModel) {
        board = boardModel;
        pacman = new Pacman(getCellLocation(getFirstEmptyCell()));
        pacman.setImmune(true);
        ghosts = new ArrayList<>();
        foodObjects = new ArrayList<>();
        obstacles = new ArrayList<>();
        upgrades = new ArrayList<>();
        for (int i = 0; i < Settings.ghostNumber; i++) {
            ghosts.add(new Ghost(getCellLocation(getRandomFreeCell()),
                    Direction.getRandomDirection()));
        }
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c] == 1) {
                    foodObjects.add(new FoodObject(new Point(c * Settings.cellSize + Settings.cellSize / 2,
                            r * Settings.cellSize + Settings.cellSize / 2)));
                }
            }
        }
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c] != 1) obstacles.add(new Obstacle(getCellLocation(new BoardCell(r, c))));
            }
        }
    }

    private BoardCell getRandomFreeCell() {
        Random random = new Random();
        int row;
        int col;
        do {
            row = random.nextInt(1, board.length-1);
            col = random.nextInt(1, board[0].length-1);
        }while (board[row][col] != 1);
        return new BoardCell(row, col);
    }

    private BoardCell getFirstEmptyCell() {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c] == 1) return new BoardCell(r, c);
            }
        }
        return null;
    }

    private Point getCellLocation(BoardCell cell) {
        int x = cell.col * Settings.cellSize + Settings.cellSize / 2;
        int y = cell.row * Settings.cellSize + Settings.cellSize / 2;
        return new Point(x, y);
    }

    public void setPacmanDirection(Direction dir) {
        pacman.setPendingDirection(dir);
    }

    public int[][] getBoard() {
        return board;
    }

    public int getHp() {
        return pacman.getHp();
    }

    public void updateModel() {
        if (lastUpdate == null) lastUpdate = LocalTime.now();
        LocalTime updateTime = LocalTime.now();
        moveCharacters(updateTime);
        processCollisions();
        createUpgrades();
        if (activeUpgrade!=null)
            activeUpgrade.checkExpiry();
        lastUpdate = updateTime;
    }

    private void createUpgrades() {
        Random rnd = new Random();
        if(lastUpgradeCreation == null) lastUpgradeCreation = LocalTime.now();
        if(Duration.between(lastUpgradeCreation, LocalTime.now()).toMillis() >= 5000) {
            for (var ghost : ghosts) {
                if(rnd.nextInt(0,4) == 0){
                    upgrades.add(createRandomUpgrade(ghost.location));
                }
            }
            lastUpgradeCreation = LocalTime.now();
        }
    }

    private Upgrade createRandomUpgrade(Point location) {
        var rnd = new Random();
        int val = rnd.nextInt(0,15);
        if(val < 5) return new SpeedUpgrade(location);
        else if(val < 9) return new ImmunityUpgrade(location);
        else if(val < 13)return new HunterUpgrade(location);
        else return new LifeUpgrade(location);
    }

    private void processCollisions() {
        ArrayList<FoodObject> foodObjectsCopy = new ArrayList<>(foodObjects);
        for (FoodObject food : foodObjectsCopy) {
            if (pacman.canEat(food)) {
                removeFood(food);
                score+=10;
            }
        }
        var upgradesCopy = new ArrayList<>(upgrades);
        for(var upgrade : upgradesCopy){
            if(pacman.collidesWith(upgrade)){
                upgrade.activate(this);
                upgrades.remove(upgrade);
            }
        }

        if((pacman.isImmune() && (activeUpgrade == null || !(activeUpgrade instanceof ImmunityUpgrade)) &&
                Duration.between(pacman.getBecameImmune(), LocalTime.now()).toMillis() > 2000)) pacman.setImmune(false);
        if(!pacman.isImmune()) {
            for (Ghost ghost : ghosts) {
                if (ghost.collidesWith(pacman)) {
                    if(pacman.isInHunterMode() && ghost.isScared()){
                        killGhost(ghost);
                    }
                    else {
                        killPacman();
                        restart();
                    }
                }
            }
        }
    }

    private void killGhost(Ghost ghost) {
        ghost.setScared(false);
        ghost.setLocation(getCellLocation(getRandomFreeCell()));
        ghost.setDirection(Direction.getRandomDirection());
        score += 200;
    }

    private void restart() {
        upgrades = new ArrayList<>();
        if(activeUpgrade != null) activeUpgrade.deactivate();
        activeUpgrade = null;
        pacman.setLocation(getCellLocation(getFirstEmptyCell()));
        pacman.stop();
        for (var ghost : ghosts) {
            ghost.setLocation(getCellLocation(getRandomFreeCell()));
            ghost.setDirection(Direction.getRandomDirection());
        }
    }

    private void killPacman() {
        pacman.reduceHp();
        if (pacman.isAlive()) {
            pacman.setImmune(true);
        }
    }

    private void removeFood(FoodObject food) {
        foodObjects.remove(food);
    }

    private void moveCharacters(LocalTime updateTime) {
        Duration passedTime = Duration.between(lastUpdate, updateTime);
        for (Character character : getAllMovingObjects()) {

            character.updateMovementDirection(getValidDirections(character));

            if (character.state == CharacterState.MOVING) {
                character.move(toVirtualTime(passedTime));
                var obstacle = character.getCollidingObject(obstacles);
                if (obstacle!=null) {
                    do {
                        character.moveBack(1);
                    }
                    while(character.collidesWith(obstacle));
                }
            }
        }
    }

    private ArrayList<Direction> getValidDirections(Character character) {
        var validDirections = new ArrayList<Direction>();
        var oldLocation = character.getLocation();
        for(Direction direction : Direction.values()) {
            character.move(Settings.cellSize-Settings.characterSize+1, direction);
            if (!character.collidesWithAny(obstacles))
                validDirections.add(direction);
            character.setLocation(oldLocation);
        }
        return validDirections;
    }

    private ArrayList<Character> getAllMovingObjects() {
        ArrayList<Character> objects = new ArrayList<>();
        objects.add(pacman);
        objects.addAll(ghosts);
        return objects;
    }

    private Duration toVirtualTime(Duration realTime) {
        long millis = realTime.toMillis();
        millis *= Settings.timeCoefficient;
        return Duration.ofMillis(millis);
    }


    private BoardCell getBoardCell(Point location) {
        int row = location.y / Settings.cellSize;
        int column = location.x / Settings.cellSize;
        if (row > board.length) return null;
        if (column > board[0].length) return null;
        return new BoardCell(row, column);
    }

    public Pacman getPacman() {
        return pacman;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<GameObject> getAllObjects() {
        ArrayList<GameObject> objects = new ArrayList<>();
        objects.add(pacman);
        objects.addAll(foodObjects);
        objects.addAll(ghosts);
        objects.addAll(upgrades);
        return objects;
    }

    public Size getBoardSize() {
        var columns = board[0].length;
        var rows = board.length;
        return new Size(columns*Settings.cellSize, rows*Settings.cellSize);
    }

    public boolean victory() {
        return foodObjects.size() == 0;
    }

    public TemporaryUpgrade getActiveUpgrade() {
        return activeUpgrade;
    }
	
    public void setActiveUpgrade(TemporaryUpgrade upgrade) {
        activeUpgrade = upgrade;
    }

    public ArrayList<Ghost> getGhosts() {
        return ghosts;
    }
}