package App.Bot.content;

import App.Bot.functions.PromoCodeService;
import App.Bot.model.PromoCode;
import App.p2pkassa.HandleUsersData;
import App.p2pkassa.PayInform;
import App.p2pkassa.model.PaymentInfo;
import App.p2pkassa.model.UserOrder;
import App.p2pkassa.model.PayData;
import App.p2pkassa.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Component
public class StringsData {
    @Autowired
    private PromoCodeService promo;
    @Autowired
    private HandleUsersData handle;

    public final String afterEmail = "Спасибо за информацию. Введите теперь Ваш пароль:";
    public final String wrongFormat = "Не верный формат. Попробуйте пожалуйста еще раз.";
    public final String passwordSaved = "Отлично! Все ваши данные успешно сохранены.\nМы храним их в зашифрованном виде, так что даже мы не можем их прочитать.\nТеперь, пожалуйста, выберите, как часто вы хотели бы проверять рейтинг. Введите период в формате: \"23 часа\", \"6 дней\" или \"3 недели\". Вы можете выбрать любой период.";
    public final String sorry = "Извините, я не смог распознать введенный вами временной период.\nПожалуйста, убедитесь, что вы вводите период в правильном формате. Попробуйте еще раз!";
    public final String welcomeBack = "Рады видеть Вас снова";
    public final String closeMenu = "Вы можете всегда вернуться к этому.";
    public final String somethingWentWrong = "Что-то пошло не так";
    public final String subscriptionEnd = "Проверьте Вашу подписку, кажется она закончилась";
    public final String noRegistered = "Вы еще не прошли регистрацию";
    public final String settingType = "Что хотите настроить?";
    public final String doYouSure = "Вы уверены?";
    public final String doYouHaveAPromo = "У вас есть промокод?";
    public final String chooseOption = "Выберите подходяший тариф:";
    public final String inputYouPromo = "Введите Ваш промокод:";
    public final String promoDoesNotExist = "Промокод не существует :(";
    public final String hooray = "Ура! Промокод найден!";
    public final String payOptions = "Выберите способ платежа:";

    public String getWelcomeMsg(String name) {
        return name + "! Добро пожаловать в чат-бот НБКИ!\n"
                + "Этот бот создан, чтобы помочь Вам следить за вашим официальным "
                + "рейтингом в Национальном Бюро Кредитной Истории. "
                + "Для начала работы, пожалуйста, введите вашу электронную "
                + "почту, которая привязана к вашему аккаунту в НБКИ.";
    }

    public String createComplete(String hours) {
        return "Вы установили период обновления " + hours + "\nНастройка успешно завершена. Прздарвляем!"
                + "\nТеперь вы можете в полной мере пользоваться всеми возможностями нашего сервиса."
                + "Желаем удачи и приятного использования!";

    }

    public PromoCode getPromoInfo(String text) {
        Optional<PromoCode> promoOpt = promo.getPromoCode(text);
        return promoOpt.orElse(null);
    }

    public String getTimerString(Long chatId) {

        return "";
    }

    public UserOrder getPayInformation(long chatId, String buttonData) {
        return handle.getPayInformation(chatId, buttonData);
    }
}