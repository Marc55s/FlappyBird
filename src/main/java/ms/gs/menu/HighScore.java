package ms.gs.menu;

import ms.gs.Main;
import ms.gs.entity.Bird;
import ms.gs.environment.PipePair;
import ms.gs.gamelogic.GameObject;
import ms.gs.gamelogic.GameState;
import ms.gs.screen.GamePanel;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.*;
import java.net.URL;

public class HighScore extends GameObject implements Serializable {

    @Serial
    private static final long serialVersionUID = -7629368922699364878L;
    private Font f = null;
    private Font f2 = null;
    private GamePanel panel;
    private Bird bird;
    private PipePair pipe;
    private PipePair pipesec;
    private File dir;
    private File serializable;
    private int score = 0;
    private int bestScore = 0;
    private boolean scoreCounterLock = false;


    public HighScore(String name, GamePanel panel, float speed, int x, int y, int width, int height) {
        super(name, speed, x, y, width, height);
        this.panel = panel;
        this.bird = panel.getBird();
        this.pipe = panel.getPipePair();
        this.pipesec = panel.getPipePairSec();

        try {
            f = Font.createFont(Font.TRUETYPE_FONT, ClassLoader.getSystemResourceAsStream("Font/04B_19__.TTF")).deriveFont(80f);
            f2 = Font.createFont(Font.TRUETYPE_FONT, ClassLoader.getSystemResourceAsStream("Font/04B_19__.TTF")).deriveFont(60f);
            dir = new File("save");
            serializable = new File("save/score.ser");
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
        if (serializable.exists()) {
            this.bestScore = getBestScoreFromFile();
        }
    }


    @Override
    public void update(long elapsedTime) {
        int birdMidPositionX = bird.getX() + (bird.getWidth() / 2);
        if (birdMidPositionX < pipe.getX() + pipe.getWidth() / 2 + Settings.HIGHSCORE_HITBOX_SIZE && birdMidPositionX > pipe.getX() + (pipe.getWidth() / 2) - Settings.HIGHSCORE_HITBOX_SIZE
                || birdMidPositionX < pipesec.getX() + pipesec.getWidth() / 2 + Settings.HIGHSCORE_HITBOX_SIZE && birdMidPositionX > pipesec.getX() + (pipesec.getWidth() / 2) - Settings.HIGHSCORE_HITBOX_SIZE) {
            if (!panel.isCollided && !scoreCounterLock) {
                scoreCounterLock = true;
                score++;
            }
        } else {
            scoreCounterLock = false;
        }
    }

    @Override
    public void render(Graphics g) {
        String s = String.valueOf((int) score);
        g.setFont(f);
        g.drawString(s, getX(), getY());
        String bestScore = "Best: " + this.bestScore;
        if (Main.gameState.equals(GameState.MENU)) {
            g.setFont(f2);
            g.drawString(bestScore, Main.WIDTH / 2 - 100, Main.HEIGHT - 400);
        } else if (Main.gameState == GameState.DEAD) {
            //TODO cool death-msg win
        }
    }

    public void resetLock() {
        scoreCounterLock = false;
    }

    private void saveBestScore() {
        if (dir.mkdir()) {
            try {
                if (serializable.createNewFile()) {
                    System.out.println("new highscorefile created at: " + serializable.getAbsolutePath());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(serializable.getAbsolutePath());
            oos = new ObjectOutputStream(fos);
            oos.writeObject(this.bestScore);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Object getHsObjFromFile() {
        FileInputStream fis = null;
        Object fileObj;
        try {
            fis = new FileInputStream(serializable.getAbsolutePath());
            ObjectInputStream ois = new ObjectInputStream(fis);
            fileObj = ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return fileObj;
    }

    public int getBestScore() {
        return bestScore;
    }

    private int getBestScoreFromFile() {
        return (int) getHsObjFromFile();
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
        saveBestScore();
    }

    public int getScore() {
        return score;
    }
}
