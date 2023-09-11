package App.Bot.functions;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Service
public class EmailValidator {

    private final String EMAIL_PATTERN =
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    private final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public boolean isValid(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
