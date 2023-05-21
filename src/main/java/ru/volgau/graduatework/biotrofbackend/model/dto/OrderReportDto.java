package ru.volgau.graduatework.biotrofbackend.model.dto;

import lombok.Data;
import ru.volgau.graduatework.biotrofbackend.dictionary.Stage;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderReportDto {

    private Long id;

    private String productName;

    private String category;

    private BigDecimal price;

    private Double weight;

    private Stage stage;

    private Boolean isShipped;

    private String clientFirstName;

    private String clientLastName;

    private String clientMiddleName;

    private String clientNumber;

    private String address;

    private Date createDate;

    private Date shipmentDate;

    private String employerUuid;
}
