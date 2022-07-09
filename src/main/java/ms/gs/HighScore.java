package ms.gs;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

public class HighScore extends GameObject{

    private Font f = null;
    private double highscore = 0;


    public HighScore(String name, float speed, int x, int y, int width, int height) {
        super(name, speed, x, y, width, height);
        try {
            f = Font.createFont(Font.TRUETYPE_FONT,new File("src/main/resources/Font/04B_19__.TTF")).deriveFont(80f);

        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void render(Graphics g) {
        String s = String.valueOf((int)highscore);

        //g.setColor(Color.WHITE);
        g.setFont(f);
        g.drawString(s,getX(),getY());
    }

    @Override
    public void update(long elapsedTime) {

    }

    public void setHighscore(double highscore) {
        this.highscore = highscore;
    }
}
