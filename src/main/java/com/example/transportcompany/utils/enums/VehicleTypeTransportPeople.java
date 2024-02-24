package com.example.transportcompany.utils.enums;

public enum VehicleTypeTransportPeople {
    SMALL_BUS(10),
    MEDIUM_BUS(30),
    BIG_BUS(50);

    private int peopleCount;

    VehicleTypeTransportPeople(int peopleCount) {
        this.peopleCount = peopleCount;
    }
}
