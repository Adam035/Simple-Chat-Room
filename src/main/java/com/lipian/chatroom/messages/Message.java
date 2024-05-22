package com.lipian.chatroom.messages;

import com.lipian.chatroom.Account;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message extends JPanel implements Serializable {
    public Account author;
    public String content;
    public JTextArea contentArea, authorArea, dateArea;
    public LocalDateTime date;

    public Message(String content, Account author) {
        super();
        this.author = author;
        this.content = content;
        date = LocalDateTime.now();
        Font font = new Font("Roboto", Font.PLAIN, 16);
        setLayout(new BorderLayout());

        Color gray = new Color(213, 220, 218);

        contentArea = new JTextArea(" ".concat(content));
        contentArea.setBackground(gray);
        contentArea.setFont(font);
        contentArea.setEditable(false);
        contentArea.setWrapStyleWord(true);
        contentArea.setLineWrap(true);

        authorArea = new JTextArea(author.username);
        authorArea.setEditable(false);
        authorArea.setFont(new Font("Roboto", Font.PLAIN, 12));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        dateArea = new JTextArea(date.format(formatter));

        dateArea.setBackground(gray);
        dateArea.setEditable(false);
        dateArea.setFont(new Font("Roboto", Font.PLAIN, 12));

        add(contentArea, BorderLayout.CENTER);
        add(authorArea, BorderLayout.SOUTH);
        add(dateArea, BorderLayout.EAST);
    }

    @Override
    public String toString() {
        return content;
    }
}
