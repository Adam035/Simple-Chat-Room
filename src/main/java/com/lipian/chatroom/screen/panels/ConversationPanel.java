package com.lipian.chatroom.screen.panels;

import com.lipian.chatroom.Account;
import com.lipian.chatroom.Client;
import com.lipian.chatroom.messages.Message;
import com.lipian.chatroom.messages.MyMessage;
import com.lipian.chatroom.screen.MyFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConversationPanel extends JPanel implements ActionListener {
    private final Account account;
    private final MyFrame frame;
    private final MessageBox messageBox;
    private final JTextField messageField;
    private final JButton sendButton, logoutButton;
    private final JScrollBar scrollBar;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public ConversationPanel(Account account, MenuPanel panel) {
        frame = Client.frame;
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (account.isConnected) disconnect();
            }
        });
        this.account = account;
        messageField = new JTextField();
        messageField.setBounds(MyFrame.width / 2 - 100, MyFrame.height / 2 + 55, 200, 25);
        messageField.setVisible(true);
        add(messageField);

        sendButton = new JButton("Send");
        sendButton.setBounds(MyFrame.width / 2 + 50, MyFrame.height / 2 + 105, 100, 40);
        sendButton.setFocusable(false);
        sendButton.setVisible(true);
        sendButton.addActionListener(this);
        add(sendButton);

        logoutButton = new JButton("Disconnect");
        logoutButton.setBounds(MyFrame.width / 2 - 150, MyFrame.height / 2 + 105, 100, 40);
        logoutButton.setFocusable(false);
        logoutButton.setVisible(true);
        logoutButton.addActionListener(this);
        add(logoutButton);

        messageBox = new MessageBox();

        JScrollPane scrollPane = new JScrollPane(messageBox, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(100, 30, MyFrame.width - 200, MyFrame.height - 200);
        add(scrollPane);

        scrollBar = scrollPane.getVerticalScrollBar();
        scrollBar.setValue(scrollBar.getMaximum());

        setPreferredSize(new Dimension(MyFrame.width, MyFrame.height));
        messageField.addActionListener(e -> sendMessage(new Message(messageField.getText(), account)));
        setFocusable(true);
        setLayout(null);
        setVisible(true);
        frame.add(this);

        try {
            Socket socket = new Socket(account.serverAddress, Client.port);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            connect(panel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Thread(this::listen).start();
    }

    private void connect(MenuPanel panel) {
        try {
            objectOutputStream.writeObject(account);
            objectOutputStream.flush();

            if (objectInputStream.readBoolean()) {
                account.isConnected = true;
                frame.remove(panel);
                frame.revalidate();
                frame.repaint();
            } else {
                account.isConnected = false;
                frame.remove(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized void listen() {
        while (this.isVisible()) {
            try {
                Message message = (Message) objectInputStream.readObject();
                receiveMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMessage(Message message) {
        if (!message.content.equals("")) {
            try {
                objectOutputStream.writeObject(message);
                objectOutputStream.flush();

                messageBox.add(new MyMessage(message.content, account));
                validate();
                scrollBar.setValue(scrollBar.getMaximum());
                messageField.setText(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void receiveMessage(Message message) {
        messageBox.add(message);
        validate();
        scrollBar.setValue(scrollBar.getMaximum());
    }

    private void disconnect() {
        account.isConnected = false;
        try {
            objectOutputStream.writeObject(new Account(account.username, false));
            objectOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame.remove(this);
        new MenuPanel();
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendButton) sendMessage(new Message(messageField.getText(), account));
        else if (e.getSource() == logoutButton) disconnect();
    }
}
