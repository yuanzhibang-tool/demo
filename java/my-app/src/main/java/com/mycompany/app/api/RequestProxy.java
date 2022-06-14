package com.mycompany.app.api;

public class RequestProxy {
    public String host;
    public int port;
    public String username;
    public String password;

    RequestProxy(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;

    }
}
