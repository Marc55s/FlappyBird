package ms.gs.environment;

import ms.gs.gamelogic.GameObject;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Background extends GameObject {

    public static int backgroundOption = 0;
    private BufferedImage background;

    public Background(String name, float speed, int x, int y, int width, int height) {
        super(name, speed, x, y, width, height);
        loadImg();
    }

    public void loadImg(){
        try {
            switch(backgroundOption) {
                case 0 -> background = ImageIO.read(new File("src\\main\\resources\\Background\\fbbackgroundV2.png"));
                case 1 -> background = ImageIO.read(new File("src\\main\\resources\\Background\\RainbowBackground.png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(long elapsedTime) {
        setX((int) (getX() - (getSpeed() * elapsedTime)));
        if (getX() <= -getWidth()) {
            setX(0);
        }
        loadImg();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(background, getX(), getY(), getWidth(), getHeight(), null);
        g.drawImage(background, getX() + getWidth(), getY(), getWidth(), getHeight(), null);
        g.drawImage(background, getX() + (2 * getWidth()), getY(), getWidth(), getHeight(), null);
    }

}
