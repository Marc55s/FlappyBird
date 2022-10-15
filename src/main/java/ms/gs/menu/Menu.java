package ms.gs.menu;

import ms.gs.Main;
import ms.gs.gamelogic.GameObject;
import ms.gs.gamelogic.GameState;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Menu extends GameObject {

    private final BufferedImage img;
    private final Font f;

    public Menu(String name, float speed, int x, int y, int width, int height) {
        super(name, speed, x, y, width, height);
        try {
            img = ImageIO.read(ClassLoader.getSystemResourceAsStream("Background/startscreentransparent.png"));
            f = Font.createFont(Font.TRUETYPE_FONT, ClassLoader.getSystemResourceAsStream("Font/04B_19__.TTF")).deriveFont(33f);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void render(Graphics g) {
        if (Main.gameState == GameState.MENU) {
            g.setColor(new Color(.1f, .1f, .3f, 0.3f));
            g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
            g.drawImage(img, getX(), getY(), getWidth(), getHeight(), null);
        }
    }

    @Override
    public void update(long elapsedTime) {
    }

}
