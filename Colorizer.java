
public class Colorizer {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m"; // black
    public static final String ANSI_RED = "\u001B[31m"; // red
    public static final String ANSI_GREEN = "\u001B[32m"; // green
    public static final String ANSI_YELLOW = "\u001B[33m"; // yellow
    public static final String ANSI_BLUE = "\u001B[34m"; // blue
    public static final String ANSI_PURPLE = "\u001B[35m"; // purple
    public static final String ANSI_CYAN = "\u001B[36m"; // cyan
    public static final String ANSI_WHITE = "\u001B[37m"; // white

    public static String colorize(String str, String color) {
        return color + str + ANSI_RESET;
    }

    public static String colorize(String str, String color, boolean bold) {
        if (bold)
            return color + "\033[1m" + str + ANSI_RESET;
        return color + str + ANSI_RESET;
    }

    public static String colorize(String str, String color, boolean bold, boolean underline) {
        if (bold && underline)
            return color + "\033[1m" + "\033[4m" + str + ANSI_RESET;
        if (bold)
            return color + "\033[1m" + str + ANSI_RESET;
        if (underline)
            return color + "\033[4m" + str + ANSI_RESET;
        return color + str + ANSI_RESET;
    }

}
