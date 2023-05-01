package ru.volgau.graduatework.biotrofbackend.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.volgau.graduatework.biotrofbackend.domain.entity.Order;
import ru.volgau.graduatework.biotrofbackend.domain.repository.OrderRepository;
import ru.volgau.graduatework.biotrofbackend.domain.service.OrderDaoService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderDaoServiceImpl implements OrderDaoService {

    private final OrderRepository repository;

    @Override
    public Order getById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public List<Order> getAll() {
        return repository.findAllByOrderByCreateDate();
    }

    @Override
    public void save(Order order) {
        repository.save(order);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void updateOrder(Long id, Order order) {
        repository.updateOrder(id, order);
    }

    @Override
    public List<Order> findAllByUuid(String uuid) {
        return repository.findAllByEmployerUuidOrderByCreateDate(uuid);
    }

    @Override
    public void updateShipmentData(Long id, Date shipmentDate) {
        repository.updateShipmentData(id, shipmentDate);
    }

    @Override
    public List<Order> getAllNotShippedOrders() {
        return repository.findAllNotShippedOrders();
    }

    @Override
    public List<Order> getAllShippedOrders() {
        return repository.findAllShippedOrders();
    }

    @Override
    public List<Order> findByCreateDateBetweenAndProductName(Date startDate, Date endDate, String productName) {
        List<Order> orders = repository.findByCreateDateBetween(startDate, endDate);
        return orders.stream()
                .filter(order -> productName.equals(order.getProduct().getProductName()))
                .collect(Collectors.toList());
    }
}
