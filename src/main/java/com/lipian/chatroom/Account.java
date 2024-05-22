package com.lipian.chatroom;

import java.io.Serializable;

public class Account implements Serializable {
    public String username, serverAddress;
    public boolean isConnected;

    public Account(String username, String serverAddress) {
        isConnected = true;
        this.username = username;
        this.serverAddress = serverAddress;
    }

    public Account(String username, boolean isConnected) {
        this.username = username;
        this.isConnected = isConnected;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Account account = (Account) obj;
        return account.username.equals(username);
    }

    @Override
    public String toString() {
        return username;
    }
}
