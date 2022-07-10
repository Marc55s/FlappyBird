package ms.gs;

import ms.gs.environment.PipePair;

import java.util.Map;

public class Collision {

    private final Map<String, GameObject> go;
    private GameObject bird;
    private GameObject pipeOne;
    private GameObject pipeTwo;
    private final int birdMidPosition;
    private double highscore = 0;
    double highscoreHitboxSize = 3;
    boolean highscoreCounterLock = false;



    public Collision(Map<String, GameObject> go) {
        this.go = go;
        bird = go.get("Bird");
        pipeOne = go.get("PipePair");
        pipeTwo = go.get("PipePairSec");
        birdMidPosition = bird.getX() + bird.getWidth() / 2;
    }
    public void onCollision() {
        if ((bird.getX() + bird.getWidth() >= pipeOne.getX() && bird.getX() <= pipeOne.getX() + pipeOne.getWidth() && bird.getY() <= pipeOne.getY() + pipeOne.getHeight())) {
            Scene.stopUpdateExceptBird = true;
        }
        if (bird.getX() + bird.getWidth() >= pipeOne.getX() && bird.getX() <= pipeOne.getX() + pipeOne.getWidth() && bird.getY() + bird.getHeight() >= pipeOne.getY() + pipeOne.getHeight() + PipePair.GAP) {
            Scene.stopUpdateExceptBird = true;
        }
        if ((bird.getX() + bird.getWidth() >= pipeTwo.getX() && bird.getX() <= pipeTwo.getX() + pipeTwo.getWidth() && bird.getY() <= pipeTwo.getY() + pipeTwo.getHeight())) {
            Scene.stopUpdateExceptBird = true;
        }
        if (bird.getX() + bird.getWidth() >= pipeTwo.getX() && bird.getX() <= pipeTwo.getX() + pipeTwo.getWidth() && bird.getY() + bird.getHeight() >= pipeTwo.getY() + pipeTwo.getHeight() + PipePair.GAP) {
            Scene.stopUpdateExceptBird = true;
        }
        if (bird.getY() + bird.getHeight() >= Main.HEIGHT - 80) {
            Scene.stopUpdateExceptBird = true;
        }

        if (birdMidPosition < pipeOne.getX() + pipeOne.getWidth()/2 + highscoreHitboxSize && birdMidPosition > pipeOne.getX() + (pipeOne.getWidth() / 2) - highscoreHitboxSize
                || birdMidPosition < pipeTwo.getX() + pipeTwo.getWidth()/2+ highscoreHitboxSize &&birdMidPosition > pipeTwo.getX() + (pipeTwo.getWidth() / 2) - highscoreHitboxSize) {
            if (!Scene.stopUpdateExceptBird && !highscoreCounterLock) {
                highscoreCounterLock = true;
                highscore++;
            }
        }else{
            highscoreCounterLock = false;
        }
    }

    public double getHighscore() {
        return highscore;
    }
}
