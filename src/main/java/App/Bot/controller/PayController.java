package App.Bot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayController {
    @GetMapping("/freekassa_notification")
    public void notification(String notification) {
        System.out.println(notification);
    }

    @GetMapping("/freekassa_bad")
    public void bad() {
        System.out.println("freekassa_bad");
    }

    @GetMapping("/freekassa_good")
    public void good() {
        System.out.println("good");
    }

}
