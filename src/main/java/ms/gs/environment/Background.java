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

    @Override
    public void update(long elapsedTime) {
        //TODO: moving background in slow
        System.out.println(getSpeed());
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(background,getX(),getY(),getWidth(),getHeight(),null);
    }

    @Override
    public void animation() {

    }
}
