package App.Bot.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class PayController {

    @PostMapping("/fkpayment_notification")
    public String notification(@RequestBody String notification) {
        System.out.println(notification);
        return "MERCHANT_ID:1000:tGn$z}hc7OnR]c@:001";
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
