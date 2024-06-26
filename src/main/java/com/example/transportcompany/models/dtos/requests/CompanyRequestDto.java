package com.example.transportcompany.models.dtos.requests;

import com.example.transportcompany.utils.enums.CompanyType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRequestDto {

     @NotNull(message = "Name should be a valid string")
     private String name;

     @Enumerated
     private CompanyType type;

     @NotNull(message = "Address should be a valid string")
     private String address;

     @NotNull(message = "Phone number should be a valid string")
     @Pattern(regexp = "^[+]?[(]?[0-9]{3}[)]?[-\\s.]?[0-9]{3}[-\\s.]?[0-9]{4,6}$", message = "Phone number must be 10 digits")
     private String phoneNumber;

     @NotNull(message = "Email should be a valid string")
     @Email()
     private String email;

}
