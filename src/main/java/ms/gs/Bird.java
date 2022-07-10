package ms.gs;

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

    final HashMap<Integer, Boolean> keyboard = new HashMap<>(); //TODO: Encapsulation concept
    private BufferedImage[] images;
    private Timer timer;
    private Skin skin;
    private boolean jumpLock;
    private int angle = 0;
    private int animationCounter = 0;


    public Bird(String name, float speed, int x, int y, int width, int height) {
        super(name, speed, x, y, width, height);
        animation();
        skin = Skin.STANDARD;
        images = new BufferedImage[3];
        loadImages();
        keyboard.put(KeyEvent.VK_SPACE, false);
    }

    void loadImages() {
        try {
            for (int i = 0; i < 3; i++) {
                images[i] = ImageIO.read(new File("src\\main\\resources\\Birds\\" + skin + "\\Flappybird_" + skin + "_" + i + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(long elapsedTime) {
        loadImages();
        if (getY() + getHeight() <= Main.HEIGHT - 80) {
            if (!Scene.stopUpdateExceptBird) {
                if (keyboard.get(KeyEvent.VK_SPACE)) {
                    angle = 320;
                    jumpBoost(elapsedTime);
                }
            }
            setSpeed(getSpeed() - (Settings.GRAVITY * elapsedTime));
            setY((int) (getY() - (getSpeed() * elapsedTime)));
            if (angle < 360 + 90 && !keyboard.get(KeyEvent.VK_SPACE))
                angle += 0.01 * elapsedTime;
        } else {
            timer.stop();
            animationCounter = 1;
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

    public Timer getTimer() {
        return timer;
    }
}
