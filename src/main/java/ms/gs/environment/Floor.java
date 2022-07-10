package ms.gs.environment;

import ms.gs.gamelogic.GameObject;
import ms.gs.Main;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Floor extends GameObject {

    private BufferedImage img;

    public Floor(String name, float speed, int x, int y, int width, int height) {
        super(name, speed, x, y, width, height);
        try {
            img = ImageIO.read(new File("src\\main\\resources\\Floor\\Flappybird_Standard_Floor.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(long elapsedTime) {
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
