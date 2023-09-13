package App.Bot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("application.properties")
public class BotConfig {
    @Value("${bot.name}")
    String botName;
    @Value("${bot.token}")
    String token;

    @Value("${p2p.kassa.key}")
    String p2pKassaKey;

    @Value("${p2p.kassa.project.id}")
    String p2pKassaProjectId;

    @Value("${p2p.kassa.json.link.one}")
    String p2pUrlOne;
    @Value("${p2p.kassa.json.link.two}")
    String p2pUrlTwo;

    @Value("${p2p.kassa.check.pay}")
    String p2pKassaUrlCheckPay;
}