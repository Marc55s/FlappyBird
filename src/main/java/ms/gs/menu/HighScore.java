package ms.gs.menu;

import ms.gs.Main;
import ms.gs.entity.Bird;
import ms.gs.environment.PipePair;
import ms.gs.gamelogic.GameObject;
import ms.gs.gamelogic.GameState;
import ms.gs.screen.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class HighScore extends GameObject implements Serializable {

    @Serial
    private static final long serialVersionUID = -7629368922699364878L;
    private Font f = null;
    private Font f2 = null;
    private GamePanel panel;
    private Bird bird;
    private PipePair pipe;
    private File dir;
    private File serializable;
    private int score = 0;
    private int bestScore = 0;
    private boolean scoreCounterLock = false;
    private final BufferedImage scoreboard;


    public HighScore(String name, GamePanel panel, float speed, int x, int y, int width, int height) {
        super(name, speed, x, y, width, height);
        this.panel = panel;
        this.bird = panel.getBird();
        this.pipe = panel.getPipePair();

        try {
            scoreboard = ImageIO.read(ClassLoader.getSystemResourceAsStream("Background/scoreboard.png"));
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
        if (birdMidPositionX < pipe.getPoints()[0].x + pipe.getWidth() / 2 + Settings.HIGHSCORE_HITBOX_SIZE && birdMidPositionX > pipe.getPoints()[0].x + pipe.getWidth() / 2 - Settings.HIGHSCORE_HITBOX_SIZE ||
                birdMidPositionX < pipe.getPoints()[1].x + pipe.getWidth() / 2 + Settings.HIGHSCORE_HITBOX_SIZE && birdMidPositionX > pipe.getPoints()[1].x + pipe.getWidth() / 2 - Settings.HIGHSCORE_HITBOX_SIZE) {
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
        Graphics2D g2 = (Graphics2D) g;
        String s = String.valueOf((int) score);
        if (Main.gameState == GameState.PLAY) {
            g.setFont(f);
            g.drawString(s, getX(), getY());
        } else if (Main.gameState.equals(GameState.MENU)) {
        } else if (Main.gameState == GameState.DEAD) {
            //TODO death-msg win
            g2.setColor(Color.WHITE);
            g2.drawImage(scoreboard, Main.WIDTH / 2 - (113 * 3) / 2, Main.HEIGHT / 2 - 100, 113 * 3, 57 * 3, null);

            //Highscore
            g.setFont(f2.deriveFont(43f));
            String bestScore = String.valueOf(this.bestScore);
            g.drawString(bestScore, Main.WIDTH / 2 + (113 * 3) / 2 - 75, Main.HEIGHT - 40 - 235 - 1);

            //normalscore
            g2.setFont(f2.deriveFont(43f));
            g2.drawString(s, Main.WIDTH / 2 + (113 * 3) / 2 - 75 + 3, Main.HEIGHT - 100 - 235 - 1);
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
