package ms.gs.gamelogic;

import ms.gs.entity.Bird;

import javax.swing.*;
import java.awt.AWTKeyStroke;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

public class GameKeys implements KeyListener {

    private Bird bird;
    private final JPanel panel;

    public GameKeys(Bird bird, JPanel panel) {
        this.bird = bird;
        this.panel = panel;
        Set<AWTKeyStroke> keys = panel.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS);
        Set<AWTKeyStroke> newKeys = new HashSet<>(keys);
        newKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0));
        panel.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, newKeys);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){

            bird.keyboard.put(e.getKeyCode(),true);
        }
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            bird.keyboard.put(e.getKeyCode(),false);
            bird.releaseLock();
        }
    }

    public Bird getBird() {
        return bird;
    }

    public void setBird(Bird bird) {
        this.bird = bird;
    }
}
