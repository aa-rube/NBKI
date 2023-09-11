package App.Bot.controller;

import App.Bot.model.FkNotification;
import org.springframework.web.bind.annotation.*;

@RestController
public class PayController {

    @PostMapping("/fkpayment_notification")
    public String notification(@ModelAttribute FkNotification notification) {
        System.out.println(notification);
        return "YES";
    }

    @GetMapping("/fkpayment_bad")
    public void bad() {
        System.out.println("fkpayment_bad");
    }

    @GetMapping("/fkpayment_good")
    public void good() {
        System.out.println("fkpayment_good");
    }
}
