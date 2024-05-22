package com.lipian.chatroom;

import com.lipian.chatroom.screen.MyFrame;
import com.lipian.chatroom.screen.panels.MenuPanel;

import javax.swing.*;


public class Client {
    public static MyFrame frame = new MyFrame();
    public static int port = 55555;
    public static JPanel panel = new MenuPanel();

    public void start() {
        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args) {
        new Client().start();
    }
}
