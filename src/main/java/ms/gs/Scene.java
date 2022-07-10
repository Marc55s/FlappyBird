package ms.gs;

import ms.gs.environment.Background;
import ms.gs.environment.Floor;
import ms.gs.environment.PipePair;
import ms.gs.menu.HighScore;
import ms.gs.menu.Menu;
import ms.gs.menu.Settings;
import ms.gs.menu.Skin;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import java.awt.AWTKeyStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.*;

public class Scene extends JPanel {

    public static boolean stopUpdateExceptBird = false;
    public static boolean startToUpdate = false;
    private final List<GameObject> gameObjects = new ArrayList<>();
    Map<String, GameObject> gameObjectHashMap = new HashMap<>();

    GameKeys gameKeys;
    Bird bird;
    Background background;
    Floor floor;
    PipePair pipePair;
    PipePair pipePairSec;
    Collision collision;
    HighScore highScore;
    Menu menu;
    JComboBox<Skin> skinOptions;

    public Scene() {
        setFocusable(true);
        setBackground(Color.DARK_GRAY);
        setup();
        gameKeys = new GameKeys(getBird());
        Skin[] all = Skin.values();
        skinOptions = new JComboBox<>(all);
        skinOptions.setBounds(10, 10, 100, 100);
        skinOptions.setVisible(true);
        Set<AWTKeyStroke> keys = this.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS);
        Set newKeys = new HashSet(keys);
        newKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0));
        this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, newKeys);
        skinOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bird.setSkin((Skin) skinOptions.getSelectedItem());
                bird.loadSkinImage();
                skinOptions.setFocusable(false);
                skinOptions.setRequestFocusEnabled(false);
                requestFocusInWindow();
            }
        });
        this.add(skinOptions);
        gameObjects.stream().forEach(gameObject -> gameObjectHashMap.put(gameObject.getName(), gameObject));
        collision = new Collision(gameObjectHashMap);
    }

    void update(long elapsedTime) {
        if (bird.keyboard.get(KeyEvent.VK_SPACE)) {
            startToUpdate = true;
            skinOptions.setVisible(false);
        }
        if (startToUpdate) {
            Main.gameState = GameState.PLAY;
            if (stopUpdateExceptBird) {
                Main.gameState = GameState.DEAD;
                bird.update(elapsedTime);
                if (Main.gameState.equals(GameState.DEAD)) {
                    restart();
                }
            } else {
                gameObjects.stream().forEach(e -> e.update(elapsedTime));
            }
            collision.onCollision();
            highScore.setHighscore(collision.getHighscore());
        }
    }

    private void restart() {
        System.out.println("Restart");
        Skin oldSkin = bird.getSkin();
        skinOptions.setVisible(true);
        gameObjects.clear();
        setup();
        gameObjects.stream().forEach(gameObject -> gameObjectHashMap.put(gameObject.getName(), gameObject));
        collision = new Collision(gameObjectHashMap);
        gameKeys.bird = bird;
        bird.setSkin(oldSkin);
        bird.loadSkinImage();
        startToUpdate = false;
        stopUpdateExceptBird = false;
        Main.gameState = GameState.MENU;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        gameObjects.forEach(e -> e.render(g));
        if (!startToUpdate) {
            menu.render(g);
        }
    }

    void setup() {
        menu = new Menu("Menu", 0.1f, 0, 300, Main.WIDTH, Main.HEIGHT);
        background = new Background("Background", Settings.BACKGROUND_VELOCITY, 0, 0, Main.WIDTH, Main.HEIGHT - 80); // FIXME: 06.07.2022 gefährliche hardcode y-position
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
        gameObjects.add(bird);
    }

    public GameKeys getGameKeys() {
        return gameKeys;
    }

    public Bird getBird() {
        return bird;
    }


}
