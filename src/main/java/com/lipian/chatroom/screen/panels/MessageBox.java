package com.lipian.chatroom.screen.panels;

import javax.swing.*;
import java.awt.*;

public class MessageBox extends JPanel {
    public MessageBox() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Roboto", Font.PLAIN, 116));
        textArea.setOpaque(false);
        JPanel blankSpace = new JPanel();
        blankSpace.add(textArea);
        add(blankSpace);
        setVisible(true);
    }

}
