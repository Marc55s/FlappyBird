package ms.gs;

import ms.gs.environment.PipePair;

import java.util.Map;

public class Collision {

    Map<String, GameObject> go;
    GameObject bird;
    GameObject pipeOne;
    GameObject pipeTwo;
    int birdMidPosition;


    public Collision(Map<String, GameObject> go) {
        this.go = go;
        bird = go.get("Bird");
        pipeOne = go.get("PipePair");
        pipeTwo = go.get("PipePairSec");
        birdMidPosition = bird.getX() + bird.getWidth() / 2;
    }

    double highscore = 0;

    public void onCollision(long elapsedTime) {

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

        if (birdMidPosition < pipeOne.getX() + pipeOne.getWidth() && birdMidPosition > pipeOne.getX() + pipeOne.getWidth() / 2
                || birdMidPosition > pipeTwo.getX() + pipeTwo.getWidth() / 2 && birdMidPosition < pipeTwo.getX() + pipeTwo.getWidth()) {
            if (!Scene.stopUpdateExceptBird) {
                //TODO: COMMENTS
                //System.out.println(Math.floor(2 * highscore));
                double a = (double) 1 / elapsedTime;
                highscore += a;
            }
        }
    }
}
