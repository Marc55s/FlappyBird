package ms.gs;

import ms.gs.environment.Background;
import ms.gs.environment.Floor;

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


    public Scene() {
        setBackground(Color.DARK_GRAY);
        setupPlayer();
    }

    boolean t = false;
    void update(long elapsedTime) {
        // FIXME: 06.07.2022 start scene
        if(bird.keyboard.get(KeyEvent.VK_SPACE))
            t = true;
        if(t)
            gameObjects.forEach(e -> e.update(elapsedTime));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        gameObjects.forEach(e -> e.render(g));
    }
    void setupPlayer(){
        background = new Background("Background",Settings.BACKGROUND_VEOLOCITY,0,0, Main.WIDTH,Main.HEIGHT-80); // FIXME: 06.07.2022 gefährliche hardcode y-position
        floor = new Floor("Floor",Settings.FLOOR_VEOLOCITY,0,Main.HEIGHT-80+4,Main.WIDTH,80);
        bird = new Bird("Bird",0f,240,320,80,50);
        //Reihenfolge beachten!
        gameObjects.add(background);
        gameObjects.add(floor);
        gameObjects.add(bird);
    }

    public Bird getBird() {
        return bird;
    }

    private void generateParticles(){
        int y = 0;
        int x = 0;
        for (int i = 0; i < 1500; i++) {
            if (i % 50 == 0 && i != 0) {
                y++;
                x = 0;
            }
            //particles.add(new Particle("Fluid #" + i, 0.2f,x * 20, y * 20, 15, 15));
            x++;
        }
    }
}
