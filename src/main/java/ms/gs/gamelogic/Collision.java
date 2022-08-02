package ms.gs.gamelogic;

import ms.gs.Main;
import ms.gs.menu.Settings;

import java.util.Map;

public class Collision {

    private final Map<String, GameObject> go;
    private final GameObject bird;
    private final GameObject pipeOne;
    private final GameObject pipeTwo;
    private final int birdMidPositionX;
    private final int birdLeftPositionX;
    private final int pipeOneMidPositionX;
    private final int pipeTwoMidPositionX;
    private double highscore = 0;
    private final double highscoreHitboxSize = 3;
    private boolean highscoreCounterLock = false;


    public Collision(Map<String, GameObject> go) {
        this.go = go;
        bird = go.get("Bird");
        pipeOne = go.get("PipePair");
        pipeTwo = go.get("PipePairSec");

        birdLeftPositionX = bird.getX() + bird.getWidth();
        birdMidPositionX = bird.getX() + (bird.getWidth() / 2);
        pipeOneMidPositionX = pipeOne.getX() + (pipeOne.getWidth() / 2);
        pipeTwoMidPositionX = pipeTwo.getX() + (pipeTwo.getWidth() / 2);
    }

    public boolean checkForCollision() {
        boolean collide = false;
        if ((birdLeftPositionX >= pipeOne.getX() && bird.getX() <= pipeOne.getX() + pipeOne.getWidth() && bird.getY() <= pipeOne.getY() + pipeOne.getHeight())) {
            collide = true;
        }

        if (birdLeftPositionX >= pipeOne.getX() && bird.getX() <= pipeOne.getX() + pipeOne.getWidth() && bird.getY() + bird.getHeight() >= pipeOne.getY() + pipeOne.getHeight() + Settings.PIPE_GAP) {
            collide = true;
        }

        if ((birdLeftPositionX >= pipeTwo.getX() && bird.getX() <= pipeTwo.getX() + pipeTwo.getWidth() && bird.getY() <= pipeTwo.getY() + pipeTwo.getHeight())) {
            collide = true;
        }

        if (birdLeftPositionX >= pipeTwo.getX() && bird.getX() <= pipeTwo.getX() + pipeTwo.getWidth() && bird.getY() + bird.getHeight() >= pipeTwo.getY() + pipeTwo.getHeight() + Settings.PIPE_GAP) {
            collide = true;
        }
        if (bird.getY() + bird.getHeight() >= Main.HEIGHT - 80) {
            collide = true;
        }

        if (birdMidPositionX < pipeOne.getX() + pipeOne.getWidth() / 2 + highscoreHitboxSize && birdMidPositionX > pipeOne.getX() + (pipeOne.getWidth() / 2) - highscoreHitboxSize
                || birdMidPositionX < pipeTwo.getX() + pipeTwo.getWidth() / 2 + highscoreHitboxSize && birdMidPositionX > pipeTwo.getX() + (pipeTwo.getWidth() / 2) - highscoreHitboxSize) {
            if (!collide && !highscoreCounterLock) {
                highscoreCounterLock = true;
                highscore++;
            }
        } else {
            highscoreCounterLock = false;
        }
        return collide;
    }

    public void resetLock() {
        highscoreCounterLock = false;
    }

    public double getHighscore() {
        return highscore;
    }


}
