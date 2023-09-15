package App.Bot.functions;

import App.Bot.content.StringsData;
import App.Bot.repository.UserRepository;
import App.Bot.model.UserNBKI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private StringsData data = new StringsData();

    public String createUser(long chatId, Update update) {
        UserNBKI userNBKI = new UserNBKI();
        userNBKI.setChatId(chatId);
        userNBKI.setUserName(update.getMessage().getFrom().getUserName());
        userNBKI.setUserName(update.getMessage().getFrom().getFirstName());
        userNBKI.setLoginNBKI(update.getMessage().getText());
        userNBKI.setPasswordNBKI("null");
        userNBKI.setRefreshPeriodInHours(0);
        userNBKI.setLastUpdate(LocalDateTime.now());
        userNBKI.setPaid(false);
        userRepository.save(userNBKI);
        return data.getWelcomeMsg(update.getMessage().getFrom().getFirstName());
    }

    public String createUserLogin(long chatId, String email) {
        Optional<UserNBKI> userNBKIOptional = userRepository.findById(chatId);

        if (userNBKIOptional.isPresent()) {
            UserNBKI u = userNBKIOptional.get();
            u.setLoginNBKI(email);
            userRepository.save(u);
            return data.afterEmail;
        }
        return data.wrongFormat;
    }

    public String createPassword(long chatId, String password) {
        Optional<UserNBKI> userNBKIOptional = userRepository.findById(chatId);

        if (userNBKIOptional.isPresent()) {
            UserNBKI user = userNBKIOptional.get();
            user.setPasswordNBKI(password);
            userRepository.save(user);
            return data.passwordSaved;
        }
        return data.wrongFormat;
    }

    public String setNewPeriodUpdate(long chatId, String messageText) {
        int period = TimeConverter.convertToHours(messageText);
        Optional<UserNBKI> userNBKIOptional = userRepository.findById(chatId);
        if (userNBKIOptional.isEmpty()) return data.sorry;

        if (!userNBKIOptional.get().getPasswordNBKI().equals("null") && period > 1) {
            UserNBKI userNBKI = userNBKIOptional.get();
            userNBKI.setRefreshPeriodInHours(period);
            userRepository.save(userNBKI);
            return data.createComplete(String.valueOf(period));
        } else {
            return data.sorry;
        }
    }

    public void setNewPeriodUpdate(long chatId, int countHours) {
        UserNBKI userNBKI = userRepository.findById(chatId).get();
        userNBKI.setRefreshPeriodInHours(countHours);
        userRepository.save(userNBKI);
    }

    public List<UserNBKI> getAllPaidUsers() {
        return userRepository.findByPaidTrue();
    }

    public Optional<UserNBKI> findUserNBKIByChatId(Long chatId) {
        return userRepository.findById(chatId);
    }

    public void saveAllUsers(List<UserNBKI> updateUsers) {
        userRepository.saveAll(updateUsers);
    }

    public void save(UserNBKI u) {
        userRepository.save(u);
    }
}