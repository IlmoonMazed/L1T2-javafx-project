package com.example.project;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.function.Consumer;

public class SocketWrapper {
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private volatile boolean listening = true;

    public SocketWrapper(String s, int port) throws IOException { // used by the client
        this.socket = new Socket(s, port);
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    public SocketWrapper(Socket s) throws IOException { // used by the server
        this.socket = s;
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    public void startListening(Consumer<Object> messageHandler) {
        new Thread(() -> {
            try {
                while (listening) {
                    Object message = read(); // Continuously read from the server
                    messageHandler.accept(message); // Pass received messages to the handler
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }).start();
    }

    public Object read() throws IOException, ClassNotFoundException {
        return ois.readObject();
    }

    public void write(Object o) throws IOException {
        oos.writeObject(o);
    }

    public void stopListening() {
        listening = false;
    }

    public void closeConnection() throws IOException {
        ois.close();
        oos.close();
    }

    public void closeSocket() throws IOException {
        socket.close();
    }
}
