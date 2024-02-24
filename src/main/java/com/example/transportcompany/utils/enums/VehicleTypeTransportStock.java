package com.example.transportcompany.utils.enums;

public enum VehicleTypeTransportStock {

    SMALL_TRUCK(8),
    BIG_TRUCK(15),
    CISTERN(20);

    private int tons;

    VehicleTypeTransportStock(int tons) {
        this.tons = tons;
    }
}
