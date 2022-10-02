package ms.gs;

import ms.gs.gamelogic.GameState;
import ms.gs.screen.GamePanel;
import com.formdev.flatlaf.*;
import com.formdev.flatlaf.intellijthemes.*;

import javax.swing.JFrame;
import javax.swing.UIManager;
import java.awt.Color;

public class Main {

    public static final int WIDTH = 480, HEIGHT = 640;
    public static boolean isRunning = true;
    public static GameState gameState = GameState.MENU;

    private Thread thread;
    private final GamePanel gamePanel;

    public Main() {
        gamePanel = new GamePanel();
        createFrame();
        setupGameLoop();
        thread.start();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Main();
    }

    private void createFrame() {
        JFrame jf = new JFrame();
        jf.setBackground(Color.BLACK);
        jf.setSize(WIDTH, HEIGHT);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        jf.setUndecorated(true);
        jf.setFocusable(true);
        gamePanel.setLayout(null);
        jf.add(gamePanel);
        jf.addKeyListener(gamePanel.getGameKeys());
        //jf.addMouseListener(gamePanel.getGameKeys());
        jf.setVisible(true);
    }

    private void setupGameLoop() {
        thread = new Thread(() -> {
            long time = 0;
            int frames = 0;
            final int FRAMES_PER_SECOND = 60;
            final long TIME_BETWEEN_UPDATES = 1_000_000_000 / FRAMES_PER_SECOND;
            final int MAX_UPDATES_BETWEEN_RENDER = 1;
            long lastUpdateTime = System.nanoTime();
            long currTime = System.currentTimeMillis();
            while (isRunning) {
                long now = System.nanoTime();
                long elapsedTime = System.currentTimeMillis() - currTime;
                currTime += elapsedTime;
                time += now - lastUpdateTime;
                int updateCount = 0;

                while (now - lastUpdateTime >= TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BETWEEN_RENDER) {
                    this.gamePanel.update(elapsedTime);
                    lastUpdateTime += TIME_BETWEEN_UPDATES;
                    updateCount++;
                    frames++;
                }
                this.gamePanel.repaint();
                if (time >= 1000000000) {
                    //System.out.println(frames);
                    frames = 0;
                    time = 0;
                }

                // if for some reason an update takes forever, we don't want to do an insane number of catchups.
                // if you were doing some sort of game that needed to keep EXACT time, you would get rid of this.

                if (now - lastUpdateTime >= TIME_BETWEEN_UPDATES) {
                    lastUpdateTime = now - TIME_BETWEEN_UPDATES;
                }

                long lastRenderTime = now;

                //Yield until it has been at least the target time between renders. This saves the CPU from hogging.
                while (now - lastRenderTime < TIME_BETWEEN_UPDATES && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
                    Thread.yield();
                    now = System.nanoTime();
                }
            }
        });
    }
}
