package ms.gs.menu;

import ms.gs.Main;
import ms.gs.gamelogic.GameObject;
import ms.gs.gamelogic.GameState;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.*;

public class HighScore extends GameObject implements Serializable {

    @Serial
    private static final long serialVersionUID = -7629368922699364878L;
    private Font f = null;
    private Font f2 = null;
    private double score = 0;
    private int highScore = 0;
    File dir;
    File serializable;



    public HighScore(String name, float speed, int x, int y, int width, int height) {
        super(name, speed, x, y, width, height);

        try {
            f = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/Font/04B_19__.TTF")).deriveFont(80f);
            f2 = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/Font/04B_19__.TTF")).deriveFont(60f);
            dir = new File("src\\main\\resources\\save");
            serializable = new File("src\\main\\resources\\save\\highscore.ser");
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
        if (serializable.exists()) {
            this.highScore = getBestScoreFromFile();
        }

    }

    private void saveBestScore() {
        // TODO: 15.07.2022 save in temp
        if (dir.mkdir()) {
            try {
                if (serializable.createNewFile()) {
                    System.out.println("highscore file created");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(serializable);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private HighScore getBestScoreObj() {
        FileInputStream fis = null;
        HighScore fileObj;
        try {
            fis = new FileInputStream(serializable);
            ObjectInputStream ois = new ObjectInputStream(fis);
            fileObj = (HighScore) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return fileObj;
    }

    private int getBestScoreFromFile() {
        return getBestScoreObj().getHighScore();
    }

    @Override
    public void update(long elapsedTime) {

    }

    @Override
    public void render(Graphics g) {
        String s = String.valueOf((int) score);
        g.setFont(f);
        g.drawString(s, getX(), getY());
        String bestScore = "Best: " + this.highScore;
        if (Main.gameState.equals(GameState.MENU)) {
            g.setFont(f2);
            g.drawString(bestScore, Main.WIDTH / 2 - 100, Main.HEIGHT - 400);
        }
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
        saveBestScore();
    }

    public void setScore(double score) {
        this.score = score;
    }

}
