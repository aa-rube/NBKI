package App.Bot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserNBKI {
    @Id
    long chatId;
    @Column(name = "user_name")
    String userName;

    String name;

    @Column(name = "login_nbki")
    String loginNBKI;

    @Column(name = "pass_nbki")
    String passwordNBKI;

    @Column(name = "how_often_do_up")
    int refreshPeriodInHours;

    @Column(name = "last_update")
    LocalDateTime lastUpdate;

    @Column(name = "is_paid")
    boolean paid;

    @Column(name = "end_subscription_time")
    LocalDateTime endSubscriptionTime;
}
