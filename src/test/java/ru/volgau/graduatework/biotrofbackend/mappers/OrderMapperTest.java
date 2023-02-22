package ru.volgau.graduatework.biotrofbackend.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.volgau.graduatework.biotrofbackend.dictionary.Stage;
import ru.volgau.graduatework.biotrofbackend.domain.entity.Order;
import ru.volgau.graduatework.biotrofbackend.model.request.CreateOrderRequest;
import ru.volgau.graduatework.biotrofbackend.model.request.UpdateOrderRequest;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


public class OrderMapperTest {

    private OrderMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(OrderMapper.class);
    }

    @Test
    public void createOrderRequestToOrder() {
        CreateOrderRequest request = new CreateOrderRequest();
        request.setProductName("productName");
        request.setCategory("category");
        request.setPrice(BigDecimal.ONE);
        request.setWeight(10.0);
        request.setWishes("wishes");
        request.setEmployerUuid("uuid");
        request.setPlannedDateOfShipment(new Date());

        Order order = mapper.createOrderRequestToOrder(request);

        assertEquals(request.getProductName(), order.getProductName());
        assertEquals(request.getCategory(), order.getCategory());
        assertEquals(request.getPrice(), order.getPrice());
        assertEquals(request.getWeight(), order.getWeight());
        assertEquals(request.getWishes(), order.getWishes());
        assertEquals(request.getEmployerUuid(), order.getEmployerUuid());
        assertEquals(request.getPlannedDateOfShipment(), order.getPlannedDateOfShipment());
        assertFalse(order.getIsShipped());
        assertEquals(Stage.WAITING_FOR_PAYMENT, order.getStage());
    }

    @Test
    public void updateOrderRequestToOrder() {
        UpdateOrderRequest request = new UpdateOrderRequest();
        request.setProductName("");
        request.setStage(Stage.DONE);
        Order order = new Order();
        order.setProductName("productName");
        order.setEmployerUuid("uuid");

        mapper.updateOrderRequestToOrder(request, order);

        assertEquals(Stage.DONE, order.getStage());
        assertEquals("productName", order.getProductName());
        assertNotNull(order.getEmployerUuid());
    }
}