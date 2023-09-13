package App.p2pkassa.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentInfo {
    private int id;
    private String country;
    private String receiver;
    private String bank_name;
    private String phone_number;
    private String card_number;
    private double amount;
    private String currency;
    private String msg;
    private LocalDateTime createTime;
    private Integer error;
    private String message;
}

