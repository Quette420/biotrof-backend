package ru.volgau.graduatework.biotrofbackend.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ChangeShipmentDataRequest {

    @NotNull
    private Date shipmentDate;
}
