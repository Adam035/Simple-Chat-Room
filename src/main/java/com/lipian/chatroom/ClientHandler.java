package com.lipian.chatroom;

import com.lipian.chatroom.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    public Account account;
    public Server server;
    private final Socket clientSocket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public ClientHandler(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;
        try {
            this.objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            this.objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Message message) {
        try {
            if (clientSocket != null && !clientSocket.isClosed()) {
                objectOutputStream.writeObject(message);
                objectOutputStream.flush();
            }
        } catch (IOException e) {
            // ignore
        }
    }

    public void login(Account account) {
        try {
            boolean isValid = server.connect(account);
            if (clientSocket != null && !clientSocket.isClosed()) {
                objectOutputStream.writeBoolean(isValid);
                objectOutputStream.flush();
            }
            if (isValid) {
                this.account = account;
                System.out.printf("%s connected", account);
            } else System.out.printf("%s failed to connect", account);
            assert clientSocket != null;
            String address = clientSocket.getInetAddress().getHostAddress();
            System.out.printf(" (%s)%n", address);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logout(Account account) {
        server.disconnect(account);
        System.out.printf("%s disconnected%n", account);
    }

    private void handle(Object receivedObject) {
        if (receivedObject instanceof Account account) {
            if (account.isConnected) login(account);
            else logout(account);
        } else if (receivedObject instanceof Message message) {
            System.out.printf("%s: %s%n", message.author, message);
            server.broadcast(message);
        }
    }

    @Override
    public void run() {
        try {
            while (!clientSocket.isClosed()) {
                try {
                    Object receivedObject = objectInputStream.readObject();
                    handle(receivedObject);
                } catch (Exception e) {
                    // ignore
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return account != null ? account.toString() : "Anonymous";
    }
}
