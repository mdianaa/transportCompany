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
@DiscriminatorValue("PERSON_LOAD")
public class Person extends Load {

    @Column(nullable = false, name = "full_name", length = 50)
    private String fullName;

    @Column(nullable = false, name = "phone_number", length = 20)
    private String phoneNumber;
}
