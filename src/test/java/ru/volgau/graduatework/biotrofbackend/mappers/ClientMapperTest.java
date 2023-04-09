package ru.volgau.graduatework.biotrofbackend.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.volgau.graduatework.biotrofbackend.domain.entity.Client;
import ru.volgau.graduatework.biotrofbackend.model.request.CreateOrderRequest;

import static org.junit.jupiter.api.Assertions.*;

public class ClientMapperTest {

    private static final String FIO = "Иванов Иван Иванович";

    private ClientMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(ClientMapper.class);
    }

    @Test
    public void createClientByCreateOrderRequest() {
        CreateOrderRequest request = new CreateOrderRequest();
        request.setClientFio(FIO);
        request.setPhoneNumber("88005553535");
        request.setAddress("Москва Большая Садовая дом 131");

        Client client = mapper.createClientByCreateOrderRequest(request);

        assertEquals("Иванов", client.getLastName());
        assertEquals("Иван", client.getFirstName());
        assertEquals("Иванович", client.getMiddleName());
        assertEquals("88005553535", client.getPhoneNumber());
        assertEquals("Москва Большая Садовая дом 131", client.getAddress());
    }

    @Test
    public void getFioValue() {
        assertEquals("Иванов", mapper.getFioValue(FIO, 0));
        assertEquals("Иван", mapper.getFioValue(FIO, 1));
        assertEquals("Иванович", mapper.getFioValue(FIO, 2));
    }
}