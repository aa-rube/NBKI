package App.Bot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class PayController {
    @GetMapping("/notification")
    public Map<String, Boolean> notification() {
        return Map.of("result", true);
    }

    @GetMapping("/bad")
    public Map<String, Boolean> bad() {
        return Map.of("error", false);
    }

    @GetMapping("/good")
    public Map<String, Boolean> good() {
        return Map.of("good", true);
    }

}
