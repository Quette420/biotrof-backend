package ru.volgau.graduatework.biotrofbackend.model.request;

import lombok.Data;
import ru.volgau.graduatework.biotrofbackend.dictionary.Stage;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class UpdateOrderRequest {

    private String productName;

    private BigDecimal price;

    private String category;

    private Double weight;

    private String wishes;

    private Date plannedDateOfShipment;

    private Stage stage;

    private Date shipmentDate;

    private Boolean isShipped;
}
