import refresh.RefreshTime;
public class Application {

    public static void main(String[] args) throws Exception {
        RefreshTime timer = new RefreshTime();

        while (true) {
            long start = System.currentTimeMillis();
            timer.refreshTime();
            System.out.println(System.currentTimeMillis() - start);
            Thread.sleep(60_0000);
            }
        }
    }

