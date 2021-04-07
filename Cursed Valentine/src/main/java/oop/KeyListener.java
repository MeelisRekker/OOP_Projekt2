package oop;

import javafx.scene.input.KeyEvent;

public interface KeyListener {

    public abstract void keyPressed(KeyEvent e);
    public abstract void keyReleased(KeyEvent e);
    public abstract void keyTyped(KeyEvent e);

}
