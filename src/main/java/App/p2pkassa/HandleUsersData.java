package App.p2pkassa;

import App.p2pkassa.model.PayData;
import App.p2pkassa.model.PaymentInfo;
import App.p2pkassa.model.UserOrder;
import App.p2pkassa.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
//"{"id":"457","country":"ru","phone_number":"+79277635740","receiver":"Полина","bank_name":"Тинькоф","amount":"699","currency":"RUB"}

@Component
public class HandleUsersData {
    @Autowired
    private OrderService orderService;
    @Autowired
    private PayInform payInfo;

    public UserOrder getPayInformation(long chatId, String buttonData) {
        try {
            String[] data = buttonData.split(":");
            LocalDateTime now = LocalDateTime.now();
            int i = (orderService.getTheTopIdElement() + 1);

            if (data[0].contains(PayData.CARD.getStr())) {
                UserOrder userOrder = new UserOrder();

                userOrder.setTimeCreated(now);
                userOrder.setUniqOrderId(i);
                userOrder.setNumberUserOrderId(i + "-" + chatId);
                userOrder.setAmount(Double.parseDouble(data[1]));
                userOrder.setCurrency(PayData.RUB.getStr());
                userOrder.setMethod(PayData.CARD.getStr());

                ObjectMapper objectMapper = new ObjectMapper();
                PaymentInfo info = objectMapper.readValue(payInfo.getPayDetails(userOrder), PaymentInfo.class);

                if (info.getError() != null) {
                    return null;
                }

                userOrder.setRemotePaymentOrderId(info.getId());
                userOrder.setMsg("Мы подготовили реквизиты для оплаты. Пожалуйста, внесите средтва в течении 30 минут.\n"
                        + "Нужно перевести точную сумму: " + info.getAmount() + info.getCurrency() + "\n"
                        + "по реквизитам " + info.getCard_number() + "\n");

                orderService.save(userOrder);
                return userOrder;
            }

            if (data[0].contains(PayData.SBP.getStr())) {
                UserOrder userOrder = new UserOrder();

                userOrder.setTimeCreated(now);
                userOrder.setUniqOrderId(i);
                userOrder.setNumberUserOrderId(i + "-" + chatId);
                userOrder.setAmount(Double.parseDouble(data[1]));
                userOrder.setCurrency(PayData.RUB.getStr());
                userOrder.setMethod(PayData.SBP.getStr());

                ObjectMapper objectMapper = new ObjectMapper();
                PaymentInfo info = objectMapper.readValue(payInfo.getPayDetails(userOrder), PaymentInfo.class);

                if (info.getError() != null) {
                    return null;
                }

                userOrder.setRemotePaymentOrderId(info.getId());
                userOrder.setMsg("Мы подготовили реквизиты для оплаты. Пожалуйста, внесите средтва в течении 30 минут.\n"
                        + "Нужно перевести точную сумму: " + info.getAmount() + info.getCurrency() + "\n"
                        + "по реквизитам: Банк " + info.getBank_name() + ", " + info.getPhone_number() + ", имя получателя: " + info.getReceiver());

                orderService.save(userOrder);
                return userOrder;
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
