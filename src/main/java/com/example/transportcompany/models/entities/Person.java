package com.example.transportcompany.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
//@DiscriminatorValue("PERSON_LOAD")
public class Person extends Load {

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;
}
