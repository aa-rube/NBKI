package App.p2pkassa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class UserOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "remote_payment_order_id")
    private int remotePaymentOrderId;

    @Column(name = "uniq_order_id")
    private int uniqOrderId;

    @Column(name = "time_created")
    private LocalDateTime timeCreated;

    private String numberUserOrderId;
    private String currency;
    private double amount;
    private String method;
    private String msg;
    private Long chatId;
    private int msgId;
    private int monthCount;
}
