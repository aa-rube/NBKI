package App.p2pkassa;

import App.Bot.config.BotConfig;
import App.p2pkassa.model.UserOrder;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class PayInform {
    @Autowired
    BotConfig config;

    public String getPayDetails(UserOrder userOrder) {
        return requestPayDetails(0, userOrder, config.getP2pUrlOne());
    }

    private String requestPayDetails(int attempt, UserOrder userOrder, String url) {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("project_id", config.getP2pKassaProjectId())
                .add("order_id", String.valueOf(userOrder.getUniqOrderId()))
                .add("amount", String.valueOf(userOrder.getAmount()))
                .add("apikey", config.getP2pKassaKey())
                .add("country", userOrder.getCurrency())
                .add("method", userOrder.getMethod())
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        try (Response response = client.newCall(request).execute()) {

            if (response.isSuccessful() && response.body() != null) {

                String resp = response.body().string();
                if (attempt < 1 && (resp.contains("Error") || resp.contains("error") || resp.contains("message"))) {
                    return requestPayDetails(attempt + 1, userOrder,config.getP2pUrlTwo());
                }
                return resp;

            } else {
                return null;
            }

        } catch (IOException e) {
            return null;
        }
    }

    public String getPayResult(String remoteOrderId) {
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