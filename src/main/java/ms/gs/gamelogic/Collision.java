package ms.gs.gamelogic;

import ms.gs.Main;
import ms.gs.menu.Settings;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.Map;

import static java.lang.Math.*;


public class Collision {

    private final Map<String, GameObject> go;
    private final GameObject bird;
    private final GameObject pipeOne;
    private final GameObject pipeTwo;
    private int angle = 0;


    public Collision(Map<String, GameObject> go) {
        this.go = go;
        bird = go.get("Bird");
        pipeOne = go.get("PipePair");
        pipeTwo = go.get("PipePairSec");
    }


    public boolean checkForCollision() {
        boolean collide = false;

        Rectangle r = new Rectangle(pipeOne.getX(), pipeOne.getY(), pipeOne.getWidth(), pipeOne.getHeight());
        Rectangle r2 = new Rectangle(pipeOne.getX(), pipeOne.getY() + pipeOne.getHeight() + Settings.PIPE_GAP - 10, pipeOne.getWidth(), pipeOne.getHeight());
        Rectangle r3 = new Rectangle(pipeTwo.getX(), pipeTwo.getY(), pipeTwo.getWidth(), pipeTwo.getHeight());
        Rectangle r4 = new Rectangle(pipeTwo.getX(), pipeTwo.getY() + pipeTwo.getHeight() + Settings.PIPE_GAP - 10, pipeTwo.getWidth(), pipeTwo.getHeight());
        Rectangle floor = new Rectangle(0, Main.HEIGHT - 80, Main.WIDTH, 80);

        Ellipse2D.Double b = new Ellipse2D.Double(0, 0, bird.getWidth(), bird.getHeight());
        AffineTransform af = new AffineTransform();
        af.translate(bird.getX() + bird.getWidth() / 2, bird.getY() + bird.getHeight() / 2);
        af.rotate(Math.toRadians(angle));
        af.translate(-bird.getWidth() / 2, -bird.getHeight() / 2);
        Shape s = af.createTransformedShape(b);

        if (s.intersects(r)) collide = true;
        if (s.intersects(r2)) collide = true;
        if (s.intersects(r3)) collide = true;
        if (s.intersects(r4)) collide = true;
        if (s.intersects(floor)) collide = true;

        return collide;
    }

    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
    }

    private Point[] rectangleRotate(int cx, int cy, int h, int w, int angle) {
        Point[] points = new Point[4];
        double alpha = toRadians(angle + 90);
        int dx = w / 2;
        int dy = h / 2;
        points[0] = new Point((int) (-dx * cos(alpha) - dy * sin(alpha) + cx), (int) (-dx * sin(alpha) + dy * cos(alpha) + cy));
        points[1] = new Point((int) (dx * cos(alpha) - dy * sin(alpha) + cx), (int) (dx * sin(alpha) + dy * cos(alpha) + cy));
        points[2] = new Point((int) (dx * cos(alpha) + dy * sin(alpha) + cx), (int) (dx * sin(alpha) - dy * cos(alpha) + cy));
        points[3] = new Point((int) (-dx * cos(alpha) + dy * sin(alpha) + cx), (int) (-dx * sin(alpha) - dy * cos(alpha) + cy));
        return points;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

}
