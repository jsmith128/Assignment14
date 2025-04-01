public class AppStarter {
    // Workaround for error I was getting.
    public static void main(final String[] args) {
        try {
            App.main(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
