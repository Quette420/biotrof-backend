package ru.volgau.graduatework.biotrofbackend.controllers.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.volgau.graduatework.biotrofbackend.domain.entity.Order;
import ru.volgau.graduatework.biotrofbackend.domain.service.EmployerDaoService;
import ru.volgau.graduatework.biotrofbackend.domain.service.OrderDaoService;
import ru.volgau.graduatework.biotrofbackend.mappers.OrderMapper;
import ru.volgau.graduatework.biotrofbackend.model.request.ChangeShipmentDataRequest;
import ru.volgau.graduatework.biotrofbackend.model.request.CreateOrderRequest;
import ru.volgau.graduatework.biotrofbackend.model.request.UpdateOrderRequest;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final EmployerDaoService employerDaoService;
    private final OrderDaoService orderDaoService;
    private final OrderMapper orderMapper;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('EMPLOYER')")
    public List<Order> getAllOrders(){
        log.info("getAllOrders()");
        return orderDaoService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('EMPLOYER')")
    public Order getOrderById(@PathVariable("id") Long id){
        log.info("getOrderById({})", id);
        return orderDaoService.getById(id);
    }

    @GetMapping("/employer/{uuid}")
    @PreAuthorize("hasAnyAuthority('EMPLOYER')")
    public List<Order> getAllEmployerOrders(@PathVariable String uuid) {
        log.info("getAllEmployerOrders({})", uuid);
        List<Order> orders = orderDaoService.findAllByUuid(uuid);
        log.info(orders.toString());
        return orders;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('EMPLOYER')")
    public void createOrder(@Valid @RequestBody CreateOrderRequest request){
        log.info("createOrder({})", request);
        Order order = orderMapper.createOrderRequestToOrder(request);
        order.setEmployerUuid(request.getEmployerUuid());
        orderDaoService.save(order);
    }

    @Transactional
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('EMPLOYER')")
    public void updateOrder(@PathVariable("id") Long id, @Valid @RequestBody UpdateOrderRequest request){
        log.info("updateOrder({}, {})",id, request);
        Order order = orderDaoService.getById(id);
        log.info("Order is: " + order.toString());
        orderMapper.updateOrderRequestToOrder(request, order);
        log.info("Order is: " + order.toString());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('EMPLOYER')")
    public void deleteOrder(@PathVariable("id") Long id){
        log.info("deleteOrder({})",id);
        orderDaoService.deleteById(id);
    }

    @GetMapping("/shipment")
    @PreAuthorize("hasAnyAuthority('WAREHOUSE_MANAGER')")
    public List<Order> getAllNotShippedOrders() {
        log.info("getAllNotShippedOrders()");
        return orderDaoService.getAllNotShippedOrders();
    }

    @PutMapping("/shipment/{id}")
    @PreAuthorize("hasAnyAuthority('WAREHOUSE_MANAGER')")
    public void changeShipmentData(@PathVariable("id") Long id, @Valid @RequestBody ChangeShipmentDataRequest request){
        log.info("changeShipmentData({}, {})", id, request);
        orderDaoService.updateShipmentData(id, request.getShipmentDate());
    }
}
