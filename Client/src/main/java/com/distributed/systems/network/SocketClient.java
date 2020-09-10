package com.distributed.systems.network;

import com.distributed.systems.controller.ClientController;
import com.distributed.systems.helpers.Constants;
import com.distributed.systems.model.Toy;

import java.io.*;
import java.net.Socket;

public class SocketClient {

    Socket clientSocket;

    BufferedReader in;

    OutputStream outputStream;
    ObjectOutputStream objectOutputStream;

    StringBuilder consoleOutput;

    ClientProtocol clientProtocol;

    public ClientController clientController;

    public void start(String ip, int port) {
        new Thread(() -> {
            startClient(ip, port);
        }).start();
    }

    public void startClient(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            consoleOutput = new StringBuilder();
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            outputStream = clientSocket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);

            clientProtocol = new ClientProtocol();

            // From Server
            while (true) {
                String response = in.readLine();
                System.out.println("Server > " + response);
                clientController.handleServerResponse(clientProtocol.processInput(response));
                if(response.equals(Constants.THANK_YOU_MESSAGE)){
                    System.out.println( "Server > " + response);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stopClient();
        }
    }

    public void sendToy(Toy toy){
        try {
            System.out.println("Sending messages to the ServerSocket");
            objectOutputStream.writeObject(toy);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopClient() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}