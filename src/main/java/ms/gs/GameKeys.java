package ms.gs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeys implements KeyListener {

    Bird bird;

    public GameKeys(Bird bird) {
        this.bird = bird;
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
}
