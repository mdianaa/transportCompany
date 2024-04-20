package com.example.transportcompany.utils.enums;

import lombok.Getter;

@Getter
public enum VehicleTypeForStockTransportation {

    SMALL_TRUCK(8),
    BIG_TRUCK(15),
    CISTERN(20);

    private int tons;

    VehicleTypeForStockTransportation(int tons) {
        this.tons = tons;
    }
}
