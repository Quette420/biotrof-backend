package ru.volgau.graduatework.biotrofbackend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.volgau.graduatework.biotrofbackend.domain.entity.Order;
import ru.volgau.graduatework.biotrofbackend.model.request.CreateOrderRequest;
import ru.volgau.graduatework.biotrofbackend.model.request.UpdateOrderRequest;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "isShipped", constant = "false")
    @Mapping(target = "stage", constant = "WAITING_FOR_PAYMENT")
    Order createOrderRequestToOrder(CreateOrderRequest request);

    Order updateOrderRequestToOrder(UpdateOrderRequest request);
}
