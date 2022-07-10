package ms.gs.menu;

import ms.gs.gamelogic.GameObject;
import ms.gs.Main;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Menu extends GameObject {

    private BufferedImage img;
    public Menu(String name, float speed, int x, int y, int width, int height) {
        super(name, speed, x, y, width, height);
        try {
            img = ImageIO.read(new File("src\\main\\resources\\Background\\startscreentransparent.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(.1f,.1f,.3f,0.6f));
        g.fillRect(0,0, Main.WIDTH,Main.HEIGHT);
        g.drawImage(img,getX(),getY(),getWidth(),getHeight(),null);
    }

    @Override
    public void update(long elapsedTime) {

    }
}
