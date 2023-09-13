package App.p2pkassa;

import java.io.IOException;

import App.Bot.config.BotConfig;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckPay {
    @Autowired
    private BotConfig config;

    public String getPayResult(int remoteOrderId) {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("id", String.valueOf(remoteOrderId))
                .add("project_id",config.getP2pKassaProjectId())
                .add("apikey",config.getP2pKassaKey())
                .build();

        Request request = new Request.Builder()
                .url(config.getP2pKassaUrlCheckPay())
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                return "Failed with response code: " + response.code();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "fail";
    }
}
