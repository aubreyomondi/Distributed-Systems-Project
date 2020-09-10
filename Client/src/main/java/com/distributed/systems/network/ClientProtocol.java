package com.distributed.systems.network;

import com.distributed.systems.helpers.Constants;

public class ClientProtocol {

    public String processInput(String input) {
        return switch (input) {
            case Constants.SERVER_CONNECTION_MESSAGE -> Constants.SERVER_CONNECTION_MESSAGE;
            case Constants.SERVER_TOY_IDENTIFICATION -> Constants.SERVER_TOY_IDENTIFICATION;
            case Constants.SERVER_TOY_INFORMATION -> Constants.SERVER_TOY_INFORMATION;
            case Constants.SERVER_TOY_MANUFACTURER -> Constants.SERVER_TOY_MANUFACTURER;
            case Constants.SERVER_ALL_DETAILS -> Constants.SERVER_ALL_DETAILS;
            default -> Constants.SUCCESS_MESSAGE;
        };
    }
}
