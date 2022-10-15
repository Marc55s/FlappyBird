package ms.gs.environment;

import ms.gs.gamelogic.GameObject;
import ms.gs.Main;
import ms.gs.menu.Settings;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class PipePair extends GameObject {

    private BufferedImage down;
    private BufferedImage up;
    private final int offsetY = 60;
    private Point[] points;

    public PipePair(String name, float speed, int x, int y, int width, int height) {
        super(name, speed, x, y, width, height);
        setY(ThreadLocalRandom.current().nextInt(offsetY - getHeight(), -140));
        try {
            down = ImageIO.read(ClassLoader.getSystemResourceAsStream("Pipe/pipe.png"));
            up = ImageIO.read(ClassLoader.getSystemResourceAsStream("Pipe/pipeReversed.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        points = spawn(2);
    }

    @Override
    public void update(long elapsedTime) {
        for (Point point : points) {
            if (point.x <= 0) {
                point.x = (int) Math.floor(point.x - (getSpeed() * elapsedTime));
            } else {
                point.x = ((int) (point.x - (getSpeed() * elapsedTime)));
            }

            if (point.x <= -getWidth()) {
                point.x = Main.WIDTH;
                point.y = ThreadLocalRandom.current().nextInt(offsetY - getHeight(), -140);
            }
        }
    }

    private Point[] spawn(int i) {
        Point[] p = new Point[i];
        for (int j = 0; j < i; j++) {
            p[j] = new Point(Main.WIDTH + (Main.WIDTH / i + 40) * j, (ThreadLocalRandom.current().nextInt(offsetY - getHeight(), -140)));
        }
        return p;
    }

    @Override
    public void render(Graphics g) {
        for (Point point : points) {
            drawPipe(g, point.x, point.y);
        }
    }

    private void drawPipe(Graphics g, int x, int y) {
        g.drawImage(down, x, y, getWidth(), getHeight(), null);
        g.drawImage(up, x, y + getHeight() + Settings.PIPE_GAP, getWidth(), getHeight(), null);
    }

    public Point[] getPoints() {
        return points;
    }
}
