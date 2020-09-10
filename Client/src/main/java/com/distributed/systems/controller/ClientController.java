package com.distributed.systems.controller;

import com.distributed.systems.helpers.Constants;
import com.distributed.systems.model.Manufacturer;
import com.distributed.systems.model.Toy;
import com.distributed.systems.network.SocketClient;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;

@Controller
public class ClientController implements Initializable {

    @FXML
    TextField toyCodeField;

    @FXML
    TextField toyNameField;

    @FXML
    TextField toyDescriptionField;

    @FXML
    TextField toyPriceField;

    @FXML
    DatePicker dateOfManufacturePicker;

    @FXML
    TextField batchNumberField;

    @FXML
    TextField companyNameField;

    @FXML
    TextField streetAddressField;

    @FXML
    TextField zipCodeField;

    @FXML
    TextField countryField;

    @SuppressWarnings("rawtypes")
    @FXML
    ComboBox comboBox;

    @FXML
    Label clientPortLabel;

    @FXML
    TextArea consoleTextArea;

    private int clientPort = 4444;

    private SocketClient socketClient;

    private StringBuilder consoleOutput = new StringBuilder();

    @FXML
    public void handleClientPort() {
        clientPort = findRandomOpenPortOnAllLocalInterfaces();
        clientPortLabel.setText(String.valueOf(clientPort));
    }

    @FXML
    public void handleSend() {
        Toy toy = null;
        String choice = (String) comboBox.getValue();
        switch (choice) {
            case Constants.TOY_IDENTIFICATION -> toy = getToyIdentification();
            case Constants.TOY_INFORMATION -> toy = getToyInformation();
            case Constants.TOY_MANUFACTURER -> toy = getToyManufacturer();
            case Constants.ALL_DETAILS -> toy = getAllDetails();
            case Constants.THANK_YOU_MESSAGE -> toy = getThankYou();
        }

        socketClient.sendToy(toy);
    }

    private Toy getToyIdentification() {
        Toy toy = new Toy();
        toy.setCode(toyCodeField.getText());
        toy.setName(toyNameField.getText());
        toy.setMessage(Constants.TOY_IDENTIFICATION);
        return toy;
    }


    private Toy getToyInformation() {
        Toy toy = new Toy();
        toy.setName(toyNameField.getText());
        toy.setDescription(toyDescriptionField.getText());
        try {
            toy.setPrice(Long.parseLong(toyPriceField.getText()));
            toy.setDateOfManufacture(getTimestamp(dateOfManufacturePicker.getValue()));
        } catch (NumberFormatException e) {
            System.err.println("No Price");
        }
        toy.setBatchNumber(batchNumberField.getText());
        toy.setMessage(Constants.TOY_INFORMATION);
        return toy;
    }

    private Toy getToyManufacturer() {
        Toy toy = new Toy();
        toy.setManufacturer(getManufacturer());
        toy.setMessage(Constants.TOY_MANUFACTURER);
        return toy;
    }

    private Manufacturer getManufacturer() {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setCompanyName(companyNameField.getText());
        manufacturer.setStreetAddress(streetAddressField.getText());
        manufacturer.setZipCode(zipCodeField.getText());
        manufacturer.setCountry(countryField.getText());
        return manufacturer;
    }

    private Toy getAllDetails() {
        Toy toy = getToyInformation();
        toy.setManufacturer(getManufacturer());
        toy.setMessage(Constants.ALL_DETAILS);
        return toy;
    }

    private Toy getThankYou() {
        Toy toy = new Toy();
        toy.setMessage(Constants.THANK_YOU_MESSAGE +" "+ Constants.UNIQUE_CODE);
        return toy;
    }

    private long getTimestamp(LocalDate localDate){
        ZoneId zoneId = ZoneId.systemDefault();
        return localDate.atStartOfDay(zoneId).toEpochSecond();
    }

    private int findRandomOpenPortOnAllLocalInterfaces() {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clientPort;
    }

    private String getTimestamp(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Date date = new Date(timestamp.getTime());
        return " [ " + date + " ] ";
    }

    public void handleServerResponse(String response){
        consoleOutput.append(getTimestamp()).append("[Server]").append("\t")
                .append(response).append("\n");
        consoleTextArea.setText(consoleOutput.toString());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clientPortLabel.setText(String.valueOf(clientPort));
        // Start Client
        String clientHost = "127.0.0.1";

        socketClient = new SocketClient();
        socketClient.start(clientHost, clientPort);
        socketClient.clientController = this;
        /* Send options */
        comboBox.setItems(FXCollections.observableArrayList(
                Constants.TOY_IDENTIFICATION,
                Constants.TOY_INFORMATION,
                Constants.TOY_MANUFACTURER,
                Constants.ALL_DETAILS,
                Constants.THANK_YOU_MESSAGE
        ));

    }
}
