package ru.volgau.graduatework.biotrofbackend.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderReportDto {

    private Long id;

    @JsonProperty("product_name")
    private String productName;

    private String category;

    private BigDecimal price;

    private Double weight;

    @JsonProperty("create_date")
    private Date createDate;
}
