package com.lipian.chatroom.messages;

import com.lipian.chatroom.Account;

import java.awt.*;
import java.io.Serializable;

public class MyMessage extends Message implements Serializable {

    public MyMessage(String content, Account author) {
        super(content, author);
        Color blue = new Color(41, 193, 255);
        contentArea.setBackground(blue);
        contentArea.setForeground(Color.white);
        dateArea.setBackground(blue);
        dateArea.setForeground(Color.white);

        contentArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        authorArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        add(contentArea, BorderLayout.CENTER);
        add(authorArea, BorderLayout.AFTER_LAST_LINE);
        add(dateArea, BorderLayout.WEST);
    }
}
