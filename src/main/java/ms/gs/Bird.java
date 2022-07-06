package ms.gs;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Bird extends GameObject {

    final HashMap<Integer, Boolean> keyboard = new HashMap<>(); //TODO: Encapsulation concept
    private BufferedImage[] images;
    private Skins skin;
    private boolean jumpLock;
    private int angle = 360-20;
    private int animationCounter = 0;

    public Bird(String name, float speed, int x, int y, int width, int height) {
        super(name, speed, x, y, width, height);
        animation();
        skin = Skins.RED;
        images = new BufferedImage[3];
        loadImages();
        keyboard.put(KeyEvent.VK_SPACE, false);
    }
    void loadImages(){
        try {
            for (int i = 0; i < 3; i++) {
                images[i] = ImageIO.read(new File("src\\main\\resources\\Birds\\"+skin+"\\Flappybird_"+skin+"_" + i + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void update(long elapsedTime) {

        if (inBounds()) {
            loadImages();
            if (keyboard.get(KeyEvent.VK_SPACE)) {
                jumpBoost(elapsedTime);
            }
            setSpeed(getSpeed() - (Settings.GRAVITY * elapsedTime));
            setY((int) (getY() - (getSpeed() * elapsedTime)));
        }
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

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(Math.toRadians(angle),getX(),getY());
        g2d.drawImage(images[animationCounter], getX(), getY(), getWidth(), getHeight(), null);
    }

    public void animation(){
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                switch (animationCounter){
                    case 0-> animationCounter++;
                    case 1-> animationCounter++;
                    case 2-> {
                        animationCounter = 0;
                    }
                }
            }
        },0,100);
    }
}
