package ru.volgau.graduatework.biotrofbackend.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.volgau.graduatework.biotrofbackend.domain.entity.Order;
import ru.volgau.graduatework.biotrofbackend.domain.service.OrderDaoService;
import ru.volgau.graduatework.biotrofbackend.model.request.CreateReportRequest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final OrderDaoService orderDaoService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<String> getAllReports(){
        return Collections.singletonList("Reports");
    }

    @PostMapping("/create/v1")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Resource> createReport(@RequestBody CreateReportRequest request) {
        String[] csvHeader = {
                "date", request.getProductName()
        };
        List<Order> orders = orderDaoService.findByCreateDateBetweenAndProductName(request.getDateFrom(), request.getDateTo(), request.getProductName());
        List<List<String>> csvBody = new ArrayList<>();
        for (Order order: orders) {
            csvBody.add(Arrays.asList(changeDateFormat(order.getCreateDate()), order.getWeight().toString()));
        }
        ByteArrayInputStream byteArrayOutputStream;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), CSVFormat.DEFAULT.withHeader(csvHeader))
        ) {
            for (List<String> record : csvBody)
                csvPrinter.printRecord(record);
            csvPrinter.flush();
            byteArrayOutputStream = new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        InputStreamResource fileInputStream = new InputStreamResource(byteArrayOutputStream);
        String csvFileName = "people.csv";
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + csvFileName);
        headers.set(HttpHeaders.CONTENT_TYPE, "text/csv");
        return new ResponseEntity<>(fileInputStream, headers, HttpStatus.OK);
    }

    @PostMapping("/create/v2")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Resource> createReportV2(@RequestBody CreateReportRequest request) {
        String[] csvHeader = {
                request.getProductName()
        };
        List<Order> orders = orderDaoService.findByCreateDateBetweenAndProductName(request.getDateFrom(), request.getDateTo(), request.getProductName());
        List<String> csvBody = new ArrayList<>();
        for (Order order: orders) {
            csvBody.add(order.getWeight().toString());
        }
        ByteArrayInputStream byteArrayOutputStream;
        try (
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), CSVFormat.DEFAULT.withHeader(csvHeader))
        ) {
            for (String record : csvBody)
                csvPrinter.printRecord(record);
            csvPrinter.flush();
            byteArrayOutputStream = new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        InputStreamResource fileInputStream = new InputStreamResource(byteArrayOutputStream);
        String csvFileName = "people.csv";
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + csvFileName);
        headers.set(HttpHeaders.CONTENT_TYPE, "text/csv");
        return new ResponseEntity<>(fileInputStream, headers, HttpStatus.OK);
    }

    private String changeDateFormat(Date date) {
        String pattern = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }
}
