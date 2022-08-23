package ms.gs.menu;

import ms.gs.environment.Background;
import ms.gs.gamelogic.GameObject;
import ms.gs.Main;
import ms.gs.gamelogic.GameState;

import javax.imageio.ImageIO;
import javax.swing.JCheckBox;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;

public class Menu extends GameObject {

    private BufferedImage img;

    JCheckBox jCheckBox = new JCheckBox("Rainbow mode?");

    public Menu(String name, float speed, int x, int y, int width, int height) {
        super(name, speed, x, y, width, height);
        jCheckBox.setBounds(360,10,100,40);
        try {
            img = ImageIO.read(new File("src\\main\\resources\\Background\\startscreentransparent.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics g) {
        if (Main.gameState == GameState.MENU) {
            g.setColor(new Color(.1f, .1f, .3f, 0.3f));
            g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
            g.drawImage(img, getX(), getY(), getWidth(), getHeight(), null);
            jCheckBox.setVisible(true);
        } else if (Main.gameState == GameState.DEAD) {
            g.setFont(new Font("Arial", Font.PLAIN, 30));
            g.drawString("You are dead", Main.WIDTH / 2 - 100, Main.HEIGHT / 2);
            g.drawString("press space to restart", Main.WIDTH / 2 - 130, Main.HEIGHT / 2 +30);
        }else{
            jCheckBox.setVisible(false);
        }
    }


    @Override
    public void update(long elapsedTime) {
        if(rainbowMode()){
            Background.backgroundOption = 1;
            //TODO: Rainbowmode
            Settings.BACKGROUND_VELOCITY = 0.4f;

        } else{
            Background.backgroundOption = 0;
            Settings.BACKGROUND_VELOCITY = 0.07f;
            //TODO: settings object to reset?
        }
    }

    public JCheckBox getjCheckBox() {
        return jCheckBox;
    }

    public boolean rainbowMode(){
        return jCheckBox.isSelected();
    }

}
