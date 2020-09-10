package com.distributed.systems.network;

import com.distributed.systems.controller.ServerController;
import com.distributed.systems.helpers.Constants;
import com.distributed.systems.model.Toy;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    private ServerSocket serverSocket;
    private Socket clientSocket;

    public ServerController serverController;

    public void start(int port) {
        new Thread(() -> {
            startServer(port);
        }).start();
    }

    public void startServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server > Ready to connect");
            clientSocket = serverSocket.accept();


            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            Toy toy = new Toy();
            toy.setMessage("Client Connected \n");
            serverController.handleConsole(toy);

            InputStream inputStream = clientSocket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            ServerProtocol serverProtocol = new ServerProtocol();

            out.println(serverProtocol.processInput(Constants.CONNECTION_MESSAGE));
            // From Client
            while(true) {
                toy = (Toy) objectInputStream.readObject();
                serverController.handleConsole(toy);
                String response = serverProtocol.processInput(toy.getMessage());
                out.println(response);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            stopServer();
        }
    }

    public void stopServer() {
        try {
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}