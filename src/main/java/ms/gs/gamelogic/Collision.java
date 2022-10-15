package ms.gs.gamelogic;

import ms.gs.Main;
import ms.gs.entity.Bird;
import ms.gs.environment.PipePair;
import ms.gs.menu.Settings;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import static java.lang.Math.*;


public class Collision {

    private final Bird bird;
    private final PipePair pipe;
    private int angle = 0;

    public Collision(Bird bird, PipePair pair) {
        this.bird = bird;
        this.pipe = pair;
    }


    public boolean checkForCollision() {
        boolean collide = false;

        Point[] points = pipe.getPoints();
        Point one = points[0];
        Point two = points[1];

        Rectangle r = new Rectangle(one.x, one.y, pipe.getWidth(), pipe.getHeight());
        Rectangle r2 = new Rectangle(one.x, one.y + pipe.getHeight() + Settings.PIPE_GAP - 10, pipe.getWidth(), pipe.getHeight());
        Rectangle r3 = new Rectangle(two.x, two.y, pipe.getWidth(), pipe.getHeight());
        Rectangle r4 = new Rectangle(two.x, two.y + pipe.getHeight() + Settings.PIPE_GAP - 10, pipe.getWidth(), pipe.getHeight());
        Rectangle floor = new Rectangle(0, Main.HEIGHT - 80, Main.WIDTH, 80);


        Ellipse2D.Double ellipse = new Ellipse2D.Double(0, 0, bird.getWidth(), bird.getHeight());
        AffineTransform af = new AffineTransform();
        af.translate(bird.getX() + (bird.getWidth() >> 1), bird.getY() + (bird.getHeight() >> 1));
        af.rotate(Math.toRadians(angle));
        af.translate(-bird.getWidth() >> 1, -bird.getHeight() >> 1);
        Shape birdHitbox = af.createTransformedShape(ellipse);

        if (birdHitbox.intersects(r)) collide = true;
        if (birdHitbox.intersects(r2)) collide = true;
        if (birdHitbox.intersects(r3)) collide = true;
        if (birdHitbox.intersects(r4)) collide = true;
        if (birdHitbox.intersects(floor)) collide = true;

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
