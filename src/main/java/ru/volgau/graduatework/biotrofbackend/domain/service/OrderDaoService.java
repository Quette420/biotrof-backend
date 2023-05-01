package ru.volgau.graduatework.biotrofbackend.domain.service;

import ru.volgau.graduatework.biotrofbackend.domain.entity.Order;

import java.util.Date;
import java.util.List;

public interface OrderDaoService {
    Order getById(Long id);

    List<Order> getAll();

    void save(Order order);

    void deleteById(Long id);

    void updateOrder(Long id, Order order);

    List<Order> findAllByUuid(String uuid);

    void updateShipmentData(Long id, Date shipmentDate);

    List<Order> getAllNotShippedOrders();

    public List<Order> getAllShippedOrders();

    List<Order> findByCreateDateBetweenAndProductName(Date startDate, Date endDate, String productName);
}
