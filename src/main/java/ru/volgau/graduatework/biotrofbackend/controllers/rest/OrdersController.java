package ru.volgau.graduatework.biotrofbackend.controllers.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.volgau.graduatework.biotrofbackend.dictionary.Role;
import ru.volgau.graduatework.biotrofbackend.domain.entity.Order;
import ru.volgau.graduatework.biotrofbackend.domain.entity.Product;
import ru.volgau.graduatework.biotrofbackend.domain.service.EmployerDaoService;
import ru.volgau.graduatework.biotrofbackend.domain.service.OrderDaoService;
import ru.volgau.graduatework.biotrofbackend.mappers.OrderMapper;
import ru.volgau.graduatework.biotrofbackend.model.dto.OrderReportDto;
import ru.volgau.graduatework.biotrofbackend.model.request.ChangeShipmentDataRequest;
import ru.volgau.graduatework.biotrofbackend.model.request.CreateOrderRequest;
import ru.volgau.graduatework.biotrofbackend.model.request.UpdateOrderRequest;
import ru.volgau.graduatework.biotrofbackend.service.ClientService;
import ru.volgau.graduatework.biotrofbackend.service.MailSender;
import ru.volgau.graduatework.biotrofbackend.service.ProductService;
import ru.volgau.graduatework.biotrofbackend.utils.DateHelper;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.volgau.graduatework.biotrofbackend.dictionary.Stage.DONE;
import static ru.volgau.graduatework.biotrofbackend.dictionary.Stage.READY_FOR_SHIPMENT;
import static ru.volgau.graduatework.biotrofbackend.utils.DateHelper.getFirstDayOfYear;

@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrderDaoService orderDaoService;
    private final OrderMapper orderMapper;
    private final ClientService clientService;
    private final ProductService productService;
    private final EmployerDaoService employerDaoService;
    private final MailSender mailSender;

    @Value("${mail.roles-to-mail-sent}")
    private Set<Role> rolesToSentEmails;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('EMPLOYER')")
    public List<Order> getAllOrders() {
        log.info("getAllOrders()");
        return orderDaoService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('EMPLOYER')")
    public Order getOrderById(@PathVariable("id") Long id) {
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

    @GetMapping("/by-year/{year}")
    @PreAuthorize("hasAnyAuthority('EMPLOYER')")
    public List<Order> getAllEmployerOrders(@PathVariable Integer year) {
        log.info("getAllEmployerOrders({})", year);
        List<Order> orders = orderDaoService.findByCreateDateBetween(getFirstDayOfYear(year), DateHelper.getLastDayOfYear(year));
        log.info(orders.toString());
        return orders;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('EMPLOYER')")
    public void createOrder(@Valid @RequestBody CreateOrderRequest request) {
        log.info("createOrder({})", request);
        Order order = orderMapper.createOrderRequestToOrder(request);
        Product product = productService.findProductOrCreateNew(request);
        productService.updateQuantity(product, request.getWeight());
        order.setProduct(product);
        order.setClient(clientService.findOrCreateNew(request));
        order.setEmployerUuid(request.getEmployerUuid());
        orderDaoService.save(order);
    }

    @Transactional
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('EMPLOYER')")
    public void updateOrder(@PathVariable("id") Long id, @Valid @RequestBody UpdateOrderRequest request) {
        log.info("updateOrder({}, {})", id, request);
        Order order = orderDaoService.getById(id);
        if(READY_FOR_SHIPMENT.equals(request.getStage())) {
            Optional<List<String>> emailsTo = employerDaoService.findEmployerEmailsByRole(rolesToSentEmails);
            emailsTo.ifPresent(strings -> mailSender.send("Уведомление о готовности заказа к отгрузке.", String.format("Заказ № %s готов к отгрузке.", id), "gsaranov@gmail.com", "gosha.saranov@mail.ru"));
        }
        boolean needCleanShipmentData = DONE.equals(order.getStage()) && !DONE.equals(request.getStage());
        orderMapper.updateOrderRequestToOrder(request, order);
        if (needCleanShipmentData) {
            order.setShipmentDate(null);
            order.setIsShipped(false);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('EMPLOYER')")
    public void deleteOrder(@PathVariable("id") Long id) {
        log.info("deleteOrder({})", id);
        orderDaoService.deleteById(id);
    }

    @GetMapping("/shipment/not-shipped")
    @PreAuthorize("hasAnyAuthority('WAREHOUSE_MANAGER')")
    public List<Order> getAllNotShippedOrders() {
        log.info("getAllNotShippedOrders()");
        return orderDaoService.getAllNotShippedOrders();
    }

    @GetMapping("/shipment/shipped")
    @PreAuthorize("hasAnyAuthority('WAREHOUSE_MANAGER')")
    public List<Order> getAllShippedOrders() {
        log.info("getAllShippedOrders()");
        return orderDaoService.getAllShippedOrders();
    }

    @PutMapping("/shipment/{id}")
    @PreAuthorize("hasAnyAuthority('WAREHOUSE_MANAGER')")
    public void changeShipmentData(@PathVariable("id") Long id, @Valid @RequestBody ChangeShipmentDataRequest request) {
        log.info("changeShipmentData({}, {})", id, request);
        orderDaoService.updateShipmentData(id, request.getShipmentDate());
    }

    @GetMapping("/by-date")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<OrderReportDto> getOrdersByDate() {
        List<Order> orders = orderDaoService.getAll();
        List<OrderReportDto> reportDtos = orders.stream().map(orderMapper::toOrderReportDto).collect(Collectors.toList());
        log.info(reportDtos.toString());
        return reportDtos;
    }
}
