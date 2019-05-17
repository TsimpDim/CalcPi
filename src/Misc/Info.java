package Misc;

public class Info {

    private static final long steps = 900000000;
    private static final int threads = Runtime.getRuntime().availableProcessors();
    private static final String HOST = "localhost";
    private static final int PORT = 1234;

    public static long getSteps() {
        return steps;
    }

    public static int getThreads() {
        return threads;
    }

    public static String getHOST() {
        return HOST;
    }

    public static int getPORT() {
        return PORT;
    }
}
