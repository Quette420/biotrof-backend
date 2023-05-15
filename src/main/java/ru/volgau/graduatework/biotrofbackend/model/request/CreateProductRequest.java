package ru.volgau.graduatework.biotrofbackend.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class CreateProductRequest {

    @NotBlank
    private String productName;

    @NotNull
    private BigDecimal price;

    @NotBlank
    private String categoryName;

    private Double quantity;
}
