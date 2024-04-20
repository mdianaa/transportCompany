package com.example.transportcompany.utils.enums;

import lombok.Getter;

@Getter
public enum VehicleTypeForPeopleTransportation {
    SMALL_BUS(10),
    MEDIUM_BUS(30),
    BIG_BUS(50);

    private int peopleCount;

    VehicleTypeForPeopleTransportation(int peopleCount) {
        this.peopleCount = peopleCount;
    }
}
