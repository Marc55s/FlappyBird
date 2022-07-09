package ms.gs;

import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.io.Serializable;

public class Main {

    public static final int WIDTH = 480, HEIGHT = 640;
    public static boolean isRunning = true;

    private final Thread gameloop;
    private final Scene scene;
    private JFrame jf;

    public Main() {
        scene = new Scene();
        createFrame();
        gameloop = new Thread(() -> {
            final int FRAMES_PER_SECOND = 60;
            final long TIME_BETWEEN_UPDATES = 1_000_000_000 / FRAMES_PER_SECOND;
            int frameCount;
            final int MAX_UPDATES_BETWEEN_RENDER = 1;
            long lastUpdateTime = System.nanoTime();
            long currTime = System.currentTimeMillis();
            while (isRunning) {
                long now = System.nanoTime();
                long elapsedTime = System.currentTimeMillis() - currTime;
                currTime += elapsedTime;

                int updateCount = 0;
                while (now - lastUpdateTime >= TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BETWEEN_RENDER) {
                    this.scene.update(elapsedTime);
                    lastUpdateTime += TIME_BETWEEN_UPDATES;
                    updateCount++;
                }

                // if for some reason an update takes forever, we don't want to do an insane number of catchups.
                // if you were doing some sort of game that needed to keep EXACT time, you would get rid of this.
                if (now - lastUpdateTime >= TIME_BETWEEN_UPDATES) {
                    lastUpdateTime = now - TIME_BETWEEN_UPDATES;
                }

                this.scene.paintImmediately(0, 0, WIDTH, HEIGHT); // instant painting

                long lastRenderTime = now;

                //Yield until it has been at least the target time between renders. This saves the CPU from hogging.
                while (now - lastRenderTime < TIME_BETWEEN_UPDATES && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
                    Thread.yield();
                    now = System.nanoTime();
                }
            }
        });
        gameloop.start();
    }

    public static void main(String[] args) {
        Main m = new Main();
    }

    void createFrame() {
        jf = new JFrame();
        jf.setSize(WIDTH, HEIGHT);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        jf.setUndecorated(true);
        jf.add(scene);
        jf.addKeyListener(new GameKeys(scene.getBird()));
        jf.setVisible(true);
    }
}
