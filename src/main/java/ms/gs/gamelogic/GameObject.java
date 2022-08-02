package ms.gs.gamelogic;

import java.awt.Graphics;
import java.io.Serializable;

public abstract class GameObject implements Serializable {

    private String name;
    private float speed;
    private int x, y;
    private int width, height;

    public GameObject(String name, float speed, int x, int y, int width, int height) {
        this.name = name;
        this.speed = speed;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void render(Graphics g);

    public abstract void update(long elapsedTime);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
