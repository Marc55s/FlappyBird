package ms.gs.environment;

import ms.gs.GameObject;
import ms.gs.Main;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PipePair extends GameObject {

    private BufferedImage down;
    private BufferedImage up;
    private final int heightDiff = 160;

    public PipePair(String name, float speed, int x, int y, int width, int height) {
        super(name, speed, x, y, width, height);
        try {
            down = ImageIO.read(new File("src\\main\\resources\\Pipe\\pipe.png"));
            up = ImageIO.read(new File("src\\main\\resources\\Pipe\\pipeReversed.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(long elapsedTime) {
        setX((int) (getX()-getSpeed()*elapsedTime));
        if(getX() <= 0) setX(Main.WIDTH);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(down,getX(),getY(),getWidth(),getHeight(),null);
        g.drawImage(up,getX(),getY()+getHeight()+heightDiff,getWidth(),getHeight(),null);
    }
}
