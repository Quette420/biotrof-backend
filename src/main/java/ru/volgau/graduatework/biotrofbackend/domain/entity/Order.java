package ru.volgau.graduatework.biotrofbackend.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.volgau.graduatework.biotrofbackend.dictionary.Stage;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_seq")
    @SequenceGenerator(name = "order_id_seq", sequenceName = "order_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "category")
    private String category;

    @Column(name = "weight")
    private Double weight;
    @Column(name = "wishes")
    private String wishes;

    @Column(name = "planned_date_of_shipment")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private Date plannedDateOfShipment;

    @Column(name = "stage")
    @Enumerated(EnumType.STRING)
    private Stage stage;

    @Column(name = "shipment_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private Date shipmentDate;

    @Column(name = "is_shipped")
    private Boolean isShipped;

    @Column(name = "client_fio")
    private String clientFio;

    @Column(name = "client_phoneNumber")
    private String clientPhoneNumber;

    @Column(name = "shipment_address")
    private String shipmentAddress;

    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private Date createDate;
    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private Date modifiedDate;

    @Column(name = "employer_uuid")
    private String employerUuid;
/*
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employer_uuid")
    private Employer employer;

 */
}
