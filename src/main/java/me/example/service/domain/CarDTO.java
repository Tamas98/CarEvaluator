package me.example.service.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarDTO {
    private String brand;
    private String type;
    private String plateNumber;
    private Integer yearOfManufacture;
    private Long calculatedValue;
    private Long drivenDistance;
}
