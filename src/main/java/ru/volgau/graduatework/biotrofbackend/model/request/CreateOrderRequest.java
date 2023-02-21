package ru.volgau.graduatework.biotrofbackend.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class CreateOrderRequest {

    @NotBlank
    private String employerUuid;

    @NotBlank
    private String productName;

    @NotBlank
    private String category;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Double weight;

    @NotNull
    private Date plannedDateOfShipment;

    private String wishes;
}
