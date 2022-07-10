package ms.gs.environment;

import ms.gs.gamelogic.GameObject;
import ms.gs.Main;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class PipePair extends GameObject {

    public static final int GAP = 160;
    private BufferedImage down;
    private BufferedImage up;
    private int offsetY = 60;

    public PipePair(String name, float speed, int x, int y, int width, int height) {
        super(name, speed, x, y, width, height);
        setY(ThreadLocalRandom.current().nextInt(offsetY - getHeight(), -140));
        try {
            down = ImageIO.read(new File("src\\main\\resources\\Pipe\\pipe.png"));
            up = ImageIO.read(new File("src\\main\\resources\\Pipe\\pipeReversed.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(long elapsedTime) {
        if (getX() <= 0)
            setX((int) Math.floor(getX() - (getSpeed() * elapsedTime)));

        setX((int) (getX() - (getSpeed() * elapsedTime)));

        if (getX() <= -getWidth()) {
            setX(Main.WIDTH + getWidth());
            setY(ThreadLocalRandom.current().nextInt(offsetY - getHeight(), -140));
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(down, getX(), getY(), getWidth(), getHeight(), null);
        g.drawImage(up, getX(), getY() + getHeight() + GAP, getWidth(), getHeight(), null);

    }

}
