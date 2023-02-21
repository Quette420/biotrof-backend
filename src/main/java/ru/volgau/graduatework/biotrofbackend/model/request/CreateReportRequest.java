package ru.volgau.graduatework.biotrofbackend.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CreateReportRequest {

    @NotBlank
    private String productName;

    @NotNull
    private Date dateFrom;

    @NotNull
    private Date dateTo;
}
