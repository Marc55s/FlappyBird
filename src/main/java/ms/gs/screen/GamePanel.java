package ms.gs.screen;

import ms.gs.Main;
import ms.gs.entity.Bird;
import ms.gs.environment.Background;
import ms.gs.environment.Floor;
import ms.gs.environment.PipePair;
import ms.gs.gamelogic.Collision;
import ms.gs.gamelogic.GameKeys;
import ms.gs.gamelogic.GameObject;
import ms.gs.gamelogic.GameState;
import ms.gs.menu.HighScore;
import ms.gs.menu.Menu;
import ms.gs.menu.Settings;
import ms.gs.menu.Skin;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamePanel extends JPanel {

    private final List<GameObject> gameObjects = new ArrayList<>();
    private final Map<String, GameObject> gameObjectHashMap = new HashMap<>();

    private final GameKeys gameKeys;
    private final JComboBox<Skin> skinOptions;
    private Bird bird;
    private Background background;
    private Floor floor;
    private PipePair pipePair;
    private PipePair pipePairSec;
    private Collision collision;
    private HighScore highScore;
    private Menu menu;

    private boolean isCollided = false;

    public GamePanel() {
        this.setDoubleBuffered(true);
        init();
        gameKeys = new GameKeys(getBird(), this);

        skinOptions = new JComboBox<>(Skin.values());
        skinOptions.setBounds(Main.WIDTH/2-75, 380, 150, 40);
        skinOptions.setFont(new Font("Monospace",Font.PLAIN,15));
        this.add(skinOptions);

        gameObjects.forEach(gameObject -> gameObjectHashMap.put(gameObject.getName(), gameObject)); //all initialized GO into Map
        collision = new Collision(gameObjectHashMap);
    }
    public void update(long elapsedTime) {
        if(Main.gameState == GameState.MENU) {
            bird.setSkin((Skin) skinOptions.getSelectedItem());
            bird.reloadImages();
            menu.update(elapsedTime);
            if (bird.keyboard.get(KeyEvent.VK_SPACE)) {
                Main.gameState = GameState.PLAY;
                skinOptions.setVisible(false);
            }
        }
        if (Main.gameState == GameState.PLAY) {
            if (isCollided) {
                Main.gameState = GameState.DEAD;
                bird.update(elapsedTime);
            } else {
                for (GameObject go : gameObjects) {
                    go.update(elapsedTime);
                }
            }
            //TODO: Collision & Highscore
            isCollided = collision.checkForCollision();
            highScore.setScore(collision.getHighscore());
        }
        if (Main.gameState.equals(GameState.DEAD)) {
            if (bird.keyboard.get(KeyEvent.VK_SPACE)) {
                System.out.println("Restart");
                restart();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        long start = System.nanoTime();
        //g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (GameObject gameObject : gameObjects) {
            gameObject.render(g2);
        }
        //System.out.println("Rendertime: "+(System.nanoTime()-start));
    }

    private void restart() {
        Skin oldSkin = bird.getSkin();

        skinOptions.setVisible(true);
        highScore.setHighScore((int) Math.max(collision.getHighscore(),highScore.getHighScore()));
        int hs = highScore.getHighScore();
        gameObjects.clear();
        init();

        //Save rainbow-state

        gameObjects.forEach(gameObject -> gameObjectHashMap.put(gameObject.getName(), gameObject));

        collision = new Collision(gameObjectHashMap);
        collision.resetLock();

        gameKeys.setBird(bird);
        highScore.setHighScore(hs); //save to File
        bird.setSkin(oldSkin);
        bird.reloadImages();
        isCollided = false;
        Main.gameState = GameState.MENU;
    }

    private void init() {
        //FIXME: 12.07.2022 clean up
        Main.gameState = GameState.MENU;
        menu = new Menu("Menu", 0.1f, 0, 300, Main.WIDTH, Main.HEIGHT);
        background = new Background("Background", Settings.BACKGROUND_VELOCITY, 0, 0, Main.WIDTH, Main.HEIGHT - 80);
        pipePair = new PipePair("PipePair", Settings.FLOOR_VELOCITY, Main.WIDTH, -280, 80, 480);
        pipePairSec = new PipePair("PipePairSec", Settings.FLOOR_VELOCITY, Main.WIDTH + Main.WIDTH / 2 + 40, -280, 80, 480);
        floor = new Floor("Floor", Settings.FLOOR_VELOCITY, 0, Main.HEIGHT - 80 + 4, Main.WIDTH, 80);
        highScore = new HighScore("HighScore", 0, 200, 100, 100, 150);
        bird = new Bird("Bird", 0f, Main.WIDTH / 2 - 50, Main.HEIGHT / 2 - 57 / 2, 57, 40);

        //Reihenfolge beachten!
        gameObjects.add(background);
        gameObjects.add(pipePair);
        gameObjects.add(pipePairSec);
        gameObjects.add(floor);
        gameObjects.add(highScore);
        gameObjects.add(menu);
        gameObjects.add(bird);
    }

    public GameKeys getGameKeys() {
        return gameKeys;
    }

    public Bird getBird() {
        return bird;
    }

}
