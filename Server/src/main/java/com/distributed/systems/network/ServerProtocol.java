package com.distributed.systems.network;

import com.distributed.systems.helpers.Constants;

public class ServerProtocol {

    public String processInput(String input) {
        return switch (input) {
            case Constants.CONNECTION_MESSAGE -> "Send Toy identification details (toy code, toy name)";
            case Constants.TOY_IDENTIFICATION -> "Send Toy information (name, description, price, date and batch number)";
            case Constants.TOY_INFORMATION -> "Send Toy manufacturer details";
            case Constants.TOY_MANUFACTURER -> "Send Toy all details";
            case Constants.ALL_DETAILS -> "Send Thank You Message";
            default -> input;
        };
    }
}