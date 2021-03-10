package util;

public class StringUtils {
    private StringUtils() {
    }

    public static String trimSpacesAndNewLines(String initial) {
        return initial.replaceAll("[\\s|\\n]", "");
    }
}
