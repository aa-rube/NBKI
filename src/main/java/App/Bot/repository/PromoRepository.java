package App.Bot.repository;

import App.Bot.model.PromoCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PromoRepository extends JpaRepository<PromoCode, Long> {
    Optional<PromoCode> findByPromo(String promo);
}
