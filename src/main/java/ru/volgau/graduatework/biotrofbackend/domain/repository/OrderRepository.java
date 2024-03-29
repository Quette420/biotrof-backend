package ru.volgau.graduatework.biotrofbackend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.volgau.graduatework.biotrofbackend.domain.entity.Order;

import java.util.Date;
import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Modifying
    @Query("update Order o " +
            "set o.product.productName = :#{#order.product.productName}, " +
            "o.price = :#{#order.price}, " +
            "o.weight = :#{#order.weight}, " +
            "o.wishes = :#{#order.wishes}, " +
            "o.plannedDateOfShipment = :#{#order.plannedDateOfShipment}, " +
            "o.stage = :#{#order.stage}, " +
            "o.wishes = :#{#order.wishes}, " +
            "o.product.category = :#{#order.product.category} " +
            "where o.id = :id")
    void updateOrder(@Param("id") Long id, @Param("order") Order order);

    List<Order> findAllByOrderByCreateDate();

    List<Order> findAllByEmployerUuidOrderByCreateDate(@Param("uuid") String uuid);

    @Modifying
    @Query("update Order o " +
            "set o.shipmentDate = :shipmentDate " +
            "where o.id = :id")
    void updateShipmentData(@Param("id") Long id, @Param("shipmentDate") Date shipmentDate);

    @Query("select o from Order o where o.stage = 'READY_FOR_SHIPMENT' and (o.isShipped = false or o.shipmentDate is null) order by o.plannedDateOfShipment")
    List<Order> findAllNotShippedOrders();

    @Query("select o from Order o where o.stage = 'DONE' and o.isShipped = true order by o.plannedDateOfShipment")
    List<Order> findAllShippedOrders();

    List<Order> findByCreateDateBetween(Date startDate, Date endDate);
}
