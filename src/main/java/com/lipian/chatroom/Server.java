package com.lipian.chatroom;

import com.lipian.chatroom.messages.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

public class Server {
    public int port = 55555;
    private ServerSocket serverSocket;
    private final ArrayList<ClientHandler> clients = new ArrayList<>();

    public Server() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        System.out.printf("Listening on port %d...%n", port);
        while (!serverSocket.isClosed()) {
            try {
                Socket clientSocket = serverSocket.accept();
                ClientHandler client = new ClientHandler(clientSocket, this);
                clients.add(client);
                Thread clientThread = new Thread(client);
                clientThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean connect(Account account) {
        return clients.stream()
                .map(client -> client.account)
                .filter(Objects::nonNull)
                .noneMatch(acc -> acc.equals(account));
    }

    public void disconnect(Account account) {
        clients.stream()
                .filter(client -> client.account != null)
                .filter(client -> client.account.equals(account))
                .findAny()
                .ifPresent(clients::remove);
    }

    public void broadcast(Message message) {
        clients.stream()
                .filter(client -> client.account != null)
                .filter(client -> !client.account.equals(message.author))
                .forEach(client -> client.sendMessage(message));
    }

    public static void main(String[] args) {
        new Server().start();
    }
}
