package ms.gs.environment;

import ms.gs.Main;
import ms.gs.gamelogic.GameObject;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Floor extends GameObject {

    private BufferedImage img;

    public Floor(String name, float speed, int x, int y, int width, int height) {
        super(name, speed, x, y, width, height);
        try {
            img = ImageIO.read(ClassLoader.getSystemResourceAsStream("Floor/Flappybird_Standard_Floor.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(long elapsedTime) {
        /*
        if x-position is negative false rounding
        (int) 1.9 -> 1
        (int) -1.9 -> -1;
        speed decreases and has to be compensated
         */
        setX((int) Math.floor(getX() - (getSpeed() * elapsedTime)));
        if (getX() <= -getWidth()) {
            setX(0);
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(img, getX(), getY(), getWidth(), getHeight(), null);
        g.drawImage(img, getX() + getWidth(), getY(), getWidth(), getHeight(), null);
        g.fillRect(0, Main.HEIGHT - getHeight(), Main.WIDTH, 4);
    }
}
