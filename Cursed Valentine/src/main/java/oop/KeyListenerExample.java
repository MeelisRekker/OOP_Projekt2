package oop;

import java.awt.*;
import java.awt.event.*;
public class KeyListenerExample extends Frame implements KeyListener {
    Label l;
    TextArea area;
    KeyListenerExample(){                                            //See fail pole seotud projektiga, lihtsalt näide

        l=new Label();
        l.setBounds(20,50,100,20);
        area = new TextArea();
        area.setBounds(20,80,300, 300);
        area.addKeyListener((java.awt.event.KeyListener) this);

        add(l);add(area);
        setSize(400,400);
        setLayout(null);
        setVisible(true);
    }
    public void keyPressed(KeyEvent e) {
        l.setText("Key Pressed");
    }
    public void keyReleased(KeyEvent e) {
        l.setText("Key Released");
    }
    public void keyTyped(KeyEvent e) {
        l.setText("Key Typed");
    }

    public static void main(String[] args) {
        new KeyListenerExample();
    }

    @Override
    public void keyPressed(javafx.scene.input.KeyEvent e) {

    }

    @Override
    public void keyReleased(javafx.scene.input.KeyEvent e) {

    }

    @Override
    public void keyTyped(javafx.scene.input.KeyEvent e) {

    }
}