package App.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FkNotification {
    private String MERCHANT_ID;
    private String AMOUNT;
    private String intid;
    private String MERCHANT_ORDER_ID;
    private String P_EMAIL;
    private String P_PHONE;
    private String CUR_ID;
    private String SIGN;
    private String us_key;
    private String payer_account;
    private String commission;
}
