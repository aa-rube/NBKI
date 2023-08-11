package model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class User {
    String telegramId;
    String userName;
    String name;
    String loginNBKI;
    String passwordNBKI;
    int refreshPeriodInHours;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime lastUpdate;
    boolean paid;

    public User(String telegramId, String userName, String name, String loginNBKI, String passwordNBKI,
                int refreshPeriodInHours, LocalDateTime lastUpdate, boolean paid) {
        this.telegramId = telegramId;
        this.userName = userName;
        this.name = name;
        this.loginNBKI = loginNBKI;
        this.passwordNBKI = passwordNBKI;
        this.refreshPeriodInHours = refreshPeriodInHours;
        this.lastUpdate = lastUpdate;
        this.paid = paid;
    }

    public String getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(String telegramId) {
        this.telegramId = telegramId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginNBKI() {
        return loginNBKI;
    }

    public void setLoginNBKI(String loginNBKI) {
        this.loginNBKI = loginNBKI;
    }

    public String getPasswordNBKI() {
        return passwordNBKI;
    }

    public void setPasswordNBKI(String passwordNBKI) {
        this.passwordNBKI = passwordNBKI;
    }

    public int getRefreshPeriodInHours() {
        return refreshPeriodInHours;
    }

    public void setRefreshPeriodInHours(int refreshPeriodInHours) {
        this.refreshPeriodInHours = refreshPeriodInHours;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
