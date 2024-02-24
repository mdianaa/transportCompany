package com.example.transportcompany.models.dtos.responses;

import com.example.transportcompany.models.entities.Load;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransportationResponseDto {

    private Set<Load> load;

}
