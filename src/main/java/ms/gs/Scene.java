package ms.gs;

import ms.gs.environment.Background;
import ms.gs.environment.Floor;
import ms.gs.environment.PipePair;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Scene extends JPanel {

    private final List<GameObject> gameObjects = new ArrayList<>();

    Bird bird;
    Background background;
    Floor floor;
    PipePair pipePair;


    public Scene() {
        setBackground(Color.DARK_GRAY);
        setup();
    }


    void update(long elapsedTime) {
        // FIXME: 06.07.2022 start scene
        gameObjects.forEach(e -> e.update(elapsedTime));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        gameObjects.forEach(e -> e.render(g));
    }

    void setup() {
        background = new Background("Background", Settings.BACKGROUND_VEOLOCITY, 0, 0, Main.WIDTH, Main.HEIGHT - 80); // FIXME: 06.07.2022 gefährliche hardcode y-position
        pipePair = new PipePair("PipePair", Settings.FLOOR_VEOLOCITY, 400, -20, 80, 300);
        floor = new Floor("Floor", Settings.FLOOR_VEOLOCITY, 0, Main.HEIGHT - 80 + 4, Main.WIDTH, 80);
        bird = new Bird("Bird", 0f, Main.WIDTH / 2 - 50, Main.HEIGHT / 2 - 57 / 2, 57, 40);
        //Reihenfolge beachten!
        gameObjects.add(background);
        gameObjects.add(pipePair);
        gameObjects.add(floor);
        gameObjects.add(bird);
    }

    public Bird getBird() {
        return bird;
    }

}
