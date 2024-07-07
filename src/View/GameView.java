package View;

import Model.*;
import Model.Upgrades.HunterUpgrade;
import Model.Upgrades.ImmunityUpgrade;
import Model.Upgrades.LifeUpgrade;
import Model.Upgrades.SpeedUpgrade;
import View.ObjectViews.FoodView;
import View.ObjectViews.GhostView;
import View.ObjectViews.ObjectView;
import View.ObjectViews.PacmanView;
import View.ObjectViews.UpgradeViews.HunterUpgradeView;
import View.ObjectViews.UpgradeViews.ImmunityUpgradeView;
import View.ObjectViews.UpgradeViews.LifeUpgradeView;
import View.ObjectViews.UpgradeViews.SpeedUpgradeView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

public class GameView extends JLayeredPane {
    private GameModel model;
    private final BoardView board;
    private ArrayList<ObjectView> objectViews = new ArrayList<>();
    private double scale;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public GameView(Board boardModel, GameModel gameModel) {
        setOpaque(true);
        this.model = gameModel;
        setLayout(null);

        board = new BoardView(boardModel);
        board.setLocation(0, 0);
        add(board);
        setLayer(board, 0);

        for(var gameObject: model.getAllObjects()) {
            var view = createObjectView(gameObject);
            add(view);
            setLayer(view, view.getLayer());
            objectViews.add(view);
        }

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                updateScaling();
            }
        });
    }

    private void updateScaling() {
        Size modelSize = model.getBoardSize();
        Dimension uiSize = this.getSize();
        if (uiSize.getWidth()==0)
            return;

        scale = 1;
        if ((double)modelSize.getWidth()/modelSize.getHeight() <= uiSize.getWidth()/uiSize.getHeight()) {
            scale = uiSize.getHeight()/modelSize.getHeight();
        }
        else {
            scale = uiSize.getWidth()/modelSize.getWidth();
        }
        var scalingHelper = new ScalingHelper(scale);

        board.setSize(scalingHelper.scaleSize(modelSize));
        board.scaleImage(scale);

        for (var gameView : objectViews) {
            gameView.setScale(scale);
        }
    }

    public void updateView() {
        updateObjects();
    }
    private ObjectView createObjectView(GameObject gameObject) {
        if (gameObject instanceof Pacman) {
            return new PacmanView((Pacman)gameObject);
        }
        else if(gameObject instanceof FoodObject){
            return new FoodView((FoodObject)gameObject);
        }
        else if(gameObject instanceof Ghost){
            return new GhostView((Ghost) gameObject);
        }
        else if(gameObject instanceof SpeedUpgrade) {
            return new SpeedUpgradeView((SpeedUpgrade) gameObject);
        }
        else if(gameObject instanceof LifeUpgrade) {
            return new LifeUpgradeView((LifeUpgrade) gameObject);
        }
        else if(gameObject instanceof ImmunityUpgrade) {
            return new ImmunityUpgradeView((ImmunityUpgrade) gameObject);
        }
        else if(gameObject instanceof HunterUpgrade) {
            return new HunterUpgradeView((HunterUpgrade) gameObject);
        }
        return null;
    }

    private void updateObjects() {
        var objects = model.getAllObjects();
        var objectViewsCopy = new ArrayList<>(objectViews);
        for (var view : objectViewsCopy) {
            if(!objects.contains(view.getObject())){
                objectViews.remove(view);
                remove(view);
            }
            view.updateObjectView();
        }
        for (var object : objects) {
            if(!getAllObjects().contains(object)){
                var view = createObjectView(object);
                add(view);
                setLayer(view, view.getLayer());
                view.setScale(scale);
                objectViews.add(view);
            }
        }
    }

    private ArrayList<GameObject> getAllObjects() {
        ArrayList<GameObject> objects = new ArrayList<>();
        for (var view : objectViews) {
            objects.add(view.getObject());
        }
        return objects;
    }
}
