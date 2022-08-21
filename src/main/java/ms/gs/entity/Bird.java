package ms.gs.entity;

import ms.gs.Main;
import ms.gs.gamelogic.GameObject;
import ms.gs.gamelogic.GameState;
import ms.gs.menu.Settings;
import ms.gs.menu.Skin;
import ms.gs.screen.GamePanel;

import javax.imageio.ImageIO;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Bird extends GameObject {

    private final BufferedImage[] images;
    public final HashMap<Integer, Boolean> keyboard; //TODO: Encapsulation concept
    private Timer timer;
    private Skin skin;
    private boolean jumpLock;
    private int angle;
    private int animationCounter;

    {
        images = new BufferedImage[3];
        keyboard = new HashMap<>();
        angle = 0;
        animationCounter = 0;
        skin = Skin.STANDARD;
    }

    public Bird(String name, float speed, int x, int y, int width, int height) {
        super(name, speed, x, y, width, height);
        animation();
        reloadImages();
        keyboard.put(KeyEvent.VK_SPACE, false);
    }

    public void reloadImages() {
        try {
            for (int i = 0; i < 3; i++) {
                images[i] = ImageIO.read(new File("src\\main\\resources\\Birds\\" + skin + "\\Flappybird_" + skin + "_" + i + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean test = false;
    long lastTime;
    long startTime;

    @Override
    public void update(long elapsedTime) {
        reloadImages();
        if (getY() + getHeight() <= Main.HEIGHT - 80) {
            if (!Main.gameState.equals(GameState.MENU)) {
                if (keyboard.get(KeyEvent.VK_SPACE)) {
                    angle = 340;
                    jumpBoost(elapsedTime);
                    test = false;
                } else {
                    if (!test) {
                        lastTime = System.nanoTime();
                    }
                    test = true;
                }
            }
            setSpeed(getSpeed() - (Settings.GRAVITY * elapsedTime));
            setY((int) (getY() - (getSpeed() * elapsedTime)));
            long fallingTime = (System.nanoTime() - lastTime) / 380_000_000;

            // TODO: 12.07.2022 defining values for falling;
            if(fallingTime >= 1)
                if (angle < 360 + 90 && !keyboard.get(KeyEvent.VK_SPACE))
                    angle += 0.28 * elapsedTime;
        } else {
            animationCounter = 1;
            timer.stop();
        }
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(Math.toRadians(angle), getX() + getWidth() / 2, getY() + getHeight() / 2);
        g2d.drawImage(images[animationCounter], getX(), getY(), getWidth(), getHeight(), null);
    }

    public void jumpBoost(long elapsedTime) {
        if (!jumpLock) {
            setSpeed(Settings.BOOST_VELOCITY * elapsedTime);
            jumpLock = true;
        }
    }

    public void releaseLock() {
        jumpLock = false;
    }

    public void animation() {
        timer = new Timer(100, new ActionListener() {
            int neg = 1;
            @Override
            public void actionPerformed(ActionEvent e) {
                animationCounter += neg;
                if (animationCounter == 2) neg = -1;
                if (animationCounter == 0) neg = 1;
            }
        });
        timer.start();
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    public Skin getSkin() {
        return skin;
    }

    public Timer getTimer() {
        return timer;
    }
}
