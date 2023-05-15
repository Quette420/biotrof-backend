package ru.volgau.graduatework.biotrofbackend.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateProductQuantityRequest {

    @NotBlank
    private String productName;

    @NotNull
    private Integer quantity;
}
