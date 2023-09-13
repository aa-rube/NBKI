package App.p2pkassa.service;

import App.p2pkassa.model.UserOrder;
import App.p2pkassa.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public void save(UserOrder userOrder) {
        orderRepository.save(userOrder);
    }

    public int getTheTopIdElement() {
        Optional<UserOrder> userOrderOpt = orderRepository.findTopByOrderByIdDesc();
        return userOrderOpt.map(UserOrder::getId).orElse(0);
    }
}
