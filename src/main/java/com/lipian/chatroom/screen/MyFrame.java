package com.lipian.chatroom.screen;

import javax.swing.*;
import java.awt.*;
public class MyFrame extends JFrame {
    public final static int width = 600, height = 400;
    public MyFrame() {
        setBounds(new Rectangle(0, 0, width, height));
        setTitle("Simple Chat Room");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
    }
}
