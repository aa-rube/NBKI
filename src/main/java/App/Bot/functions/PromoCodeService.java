package App.Bot.functions;

import App.Bot.model.PromoCode;
import App.Bot.repository.PromoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PromoCodeService {
    @Autowired
    private PromoRepository promoRepository;

    public void createPromo(String promo, double coefficient) {
        PromoCode p = new PromoCode();
        p.setCoefficient(coefficient);
        p.setPromo(promo);
        promoRepository.save(p);
    }

    public Optional<PromoCode> getPromoCode(String promo) {
        return promoRepository.findByPromo(promo);
    }


}
