package App.p2pkassa.model;

public enum PayData {
    RUB("ru"),
    CARD("card"),
    SBP("sbp");

    private final String str;

    PayData(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
