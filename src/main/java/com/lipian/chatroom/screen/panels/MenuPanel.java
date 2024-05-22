package com.lipian.chatroom.screen.panels;

import com.lipian.chatroom.Account;
import com.lipian.chatroom.Client;
import com.lipian.chatroom.screen.MyFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class MenuPanel extends JPanel implements ActionListener {
    private final JTextField nickField, serverAddressField;
    private final JButton connectButton;

    public MenuPanel() {
        MyFrame frame = Client.frame;

        connectButton = new JButton("Connect");
        connectButton.setBounds(MyFrame.width / 2 - 50, MyFrame.height / 2 + 50, 100, 40);
        connectButton.setFocusable(false);
        connectButton.setVisible(true);
        connectButton.addActionListener(this);
        add(connectButton);

        serverAddressField = new JTextField();
        serverAddressField.setBounds(MyFrame.width / 2 - 75, MyFrame.height / 2 - 65, 150, 25);
        serverAddressField.setVisible(true);
        add(serverAddressField);

        nickField = new JTextField();
        nickField.setBounds(MyFrame.width / 2 - 75, MyFrame.height / 2 - 20, 150, 25);
        nickField.setVisible(true);
        nickField.addActionListener(this);
        add(nickField);

        setPreferredSize(new Dimension(MyFrame.width, MyFrame.height));
        setFocusable(true);
        setLayout(null);
        setVisible(true);
        frame.add(this);
    }

    private void connect(Account account) {
        if (!account.username.equals(""))
            new ConversationPanel(account, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == connectButton)
            connect(new Account(nickField.getText(), serverAddressField.getText()));
    }
}
