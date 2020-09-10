package com.distributed.systems.controller;

import com.distributed.systems.helpers.Constants;
import com.distributed.systems.model.Toy;
import com.distributed.systems.network.SocketServer;
import com.distributed.systems.service.ToyService;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ResourceBundle;

@Controller
public class ServerController implements Initializable {


    @FXML
    Label serverPortLabel;

    @FXML
    TextArea consoleTextArea;

    private int serverPort = 4444;

    SocketServer socketServer;

    StringBuilder consoleBuilder = new StringBuilder();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serverPortLabel.setText(String.valueOf(serverPort));
        // Start Server
        socketServer = new SocketServer();
        socketServer.serverController = this;
        socketServer.start(serverPort);
    }

    @FXML
    public void handleServerPort() {
        serverPort = findRandomOpenPortOnAllLocalInterfaces();
        serverPortLabel.setText(String.valueOf(serverPort));
    }

    private int findRandomOpenPortOnAllLocalInterfaces() {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverPort;
    }

    private String getTimestamp(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Date date = new Date(timestamp.getTime());
        return " [ " + date + " ] ";
    }

    public void handleConsole(Toy toy) {
        switch (toy.getMessage()) {
            case Constants.CONNECTION_MESSAGE -> consoleBuilder.append(getTimestamp()).append("Client Connected").append("\n");
            case Constants.TOY_IDENTIFICATION -> consoleBuilder.append(getTimestamp()).append("[Toy]").append("\t")
                    .append(toy.getCode()).append("\t")
                    .append(toy.getName()).append("\t")
                    .append("\n");
            case Constants.TOY_INFORMATION -> consoleBuilder.append(getTimestamp()).append("[Toy]").append("\t")
                    .append(toy.getName()).append("\t")
                    .append(toy.getDescription()).append("\t")
                    .append(toy.getPrice()).append("\t")
                    .append(new Date(toy.getDateOfManufacture() * 1000)).append("\t")
                    .append(toy.getBatchNumber()).append("\t")
                    .append("\n");
            case Constants.TOY_MANUFACTURER -> consoleBuilder.append(getTimestamp()).append("[Manufacturer]").append("\t")
                    .append(toy.getManufacturer().getStreetAddress()).append("\t")
                    .append(toy.getManufacturer().getCompanyName()).append("\t")
                    .append(toy.getManufacturer().getCountry()).append("\t")
                    .append("\n");
            case Constants.ALL_DETAILS -> consoleBuilder.append(getTimestamp()).append("[Toy]").append("\t")
                    .append(toy.getName()).append(" ")
                    .append(toy.getDescription()).append(" ")
                    .append(toy.getPrice()).append(" ")
                    .append(new Date(toy.getDateOfManufacture())).append(" ")
                    .append(toy.getBatchNumber()).append(" ")
                    .append("[Manufacturer]").append(" ")
                    .append(toy.getManufacturer().getStreetAddress()).append(" ")
                    .append(toy.getManufacturer().getCompanyName()).append(" ")
                    .append(toy.getManufacturer().getCountry()).append(" ")
                    .append("\n");
            default -> consoleBuilder.append(getTimestamp()).append(toy.getMessage()).append("\n");
        }
        consoleTextArea.setText(consoleBuilder.toString());
    }
}