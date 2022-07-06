package ms.gs.environment;

import ms.gs.GameObject;
import ms.gs.Main;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Background extends GameObject {

    private BufferedImage background;

    public Background(String name, float speed, int x, int y, int width, int height) {
        super(name, speed, x, y, width, height);
        try {
            background = ImageIO.read(new File("src\\main\\resources\\Background\\fbbackground.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    int offsetX = 20;
    @Override
    public void update(long elapsedTime) {
        //TODO: moving background in slow
       // setX((int) (getX() - (getSpeed() * elapsedTime)));
        if (getX() <= -getWidth()+offsetX) {
            setX(0);
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(background,getX(),getY(),getWidth(),getHeight(),null);
        g.drawImage(background,getX()+getWidth()-offsetX,getY(),getWidth(),getHeight(),null);
    }

}
