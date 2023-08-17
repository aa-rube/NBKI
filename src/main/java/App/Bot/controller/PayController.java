package App.Bot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class PayController {
    @GetMapping("/notification")
    public void notification(String notification) {
        System.out.println(notification);
    }

    @GetMapping("/bad")
    public void bad() {
        System.out.println("bad");
    }

    @GetMapping("/good")
    public void good() {
        System.out.println("good");
    }

}
