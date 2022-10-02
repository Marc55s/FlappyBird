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
    private final int birdMidPositionX;
    private int angle = 0;


    public Collision(Map<String, GameObject> go) {
        this.go = go;
        bird = go.get("Bird");
        pipeOne = go.get("PipePair");
        pipeTwo = go.get("PipePairSec");

        birdMidPositionX = bird.getX() + (bird.getWidth() / 2);
    }


    public boolean checkForCollision() {
        boolean collide = false;

        Polygon p = new Polygon();
        Rectangle r = new Rectangle(pipeOne.getX(), pipeOne.getY(), pipeOne.getWidth(), pipeOne.getHeight());
        Rectangle r2 = new Rectangle(pipeOne.getX(), pipeOne.getY() + pipeOne.getHeight() + Settings.PIPE_GAP - 10, pipeOne.getWidth(), pipeOne.getHeight());
        Rectangle r3 = new Rectangle(pipeTwo.getX(), pipeTwo.getY(), pipeTwo.getWidth(), pipeTwo.getHeight());
        Rectangle r4 = new Rectangle(pipeTwo.getX(), pipeTwo.getY() + pipeTwo.getHeight() + Settings.PIPE_GAP - 10, pipeTwo.getWidth(), pipeTwo.getHeight());
        Rectangle floor = new Rectangle(0, Main.HEIGHT - 80, Main.WIDTH, 80);

        Point[] ps = rectangleRotate(birdMidPositionX, bird.getY() + bird.getHeight() / 2, bird.getWidth(), bird.getHeight(), angle);
        p.addPoint(ps[0].x, ps[0].y);
        p.addPoint(ps[1].x, ps[1].y);
        p.addPoint(ps[2].x, ps[2].y);
        p.addPoint(ps[3].x, ps[3].y);


        if (p.intersects(r)) collide = true;
        if (p.intersects(r2)) collide = true;
        if (p.intersects(r3)) collide = true;
        if (p.intersects(r4)) collide = true;
        if (p.intersects(floor)) collide = true;

        return collide;
    }


    public void render(Graphics g) {
        /*
        g.setColor(Color.RED);
        if (p != null)
           g.drawPolygon(p);
         */
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

    public void setEllipse(Ellipse2D e) {
    }
}
