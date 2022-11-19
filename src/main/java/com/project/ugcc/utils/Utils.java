package com.project.ugcc.utils;

import java.time.LocalDateTime;

public class Utils {

    private final static String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

    private final static int RANDOM_STRING_LENGTH = 20;

    private final static String ROBOTS = "User-agent: Googlebot\n" +
                                         "Disallow: /a-panel\n" +
                                         "Disallow: /login\n" +
                                         "\n" +
                                         "User-agent: *\n" +
                                         "Allow: /\n" +
                                         "Allow: /news\n" +
                                         "Allow: /news/\n" +
                                         "Allow: /news/section/\n" +
                                         "Allow: /documents\n" +
                                         "Allow: /documents/\n" +
                                         "Allow: /article/\n" +
                                         "Allow: /kahetyzm-ugcc\n" +
                                         "Allow: /kahehyzm\n" +
                                         "Allow: /gallery\n" +
                                         "Allow: /gallery/\n" +
                                         "Allow: /albums/\n" +
                                         "Allow: /contacts\n" +
                                         "\n" +
                                         "Sitemap: %s/sitemap.txt\n";


    private Utils() {
    }

    public static String transliterateStringFromCyrillicToLatinChars(String message) {
        if (message == null || message.isEmpty()) return "";

        char[] abcCyr = {' ', 'а', 'б', 'в', 'г', 'д', 'е', 'є', 'ё', 'ж', 'з', 'і', 'ї', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я', 'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Є', 'Ё', 'Ж', 'З', 'І', 'Ї', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
        String[] abcLat = {"-", "a", "b", "v", "g", "d", "e", "e", "e", "zh", "z", "i", "yi", "i", "y", "k", "l", "m", "n", "o", "p", "r", "s", "t", "u", "f", "h", "ts", "ch", "sh", "sch", "", "i", "", "e", "ju", "ja", "A", "B", "V", "G", "D", "E", "E", "E", "Zh", "Z", "І", "YI", "I", "Y", "K", "L", "M", "N", "O", "P", "R", "S", "T", "U", "F", "H", "Ts", "Ch", "Sh", "Sch", "", "I", "", "E", "Ju", "Ja", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            for (int x = 0; x < abcCyr.length; x++) {
                if (message.charAt(i) == abcCyr[x]) {
                    builder.append(abcLat[x]);
                }
            }
        }
        return builder.toString();
    }

    public static String convertDateToStringWithUkrainianMonth(LocalDateTime date) {
        if (date == null) date = LocalDateTime.of(2022, 1, 1, 1, 1);

        return date.getDayOfMonth() + " " +
               convertIntMonthToUkraineMonth(date.getMonthValue()) + " " +
               date.getYear();
    }

    private static String convertIntMonthToUkraineMonth(int month) {
        String[] ukMonths = new String[]{"січня", "лютого", "березня", "квітня", "травня", "червня", "липня", "серпня", "вересня", "жовтня", "листопада", "грудня"};
        return ukMonths[month - 1];
    }

    public static String generateRandomName() {
        StringBuilder randomString = new StringBuilder(RANDOM_STRING_LENGTH);

        for (int i = 0; i < RANDOM_STRING_LENGTH; i++) {

            int index = (int) (ALPHA_NUMERIC_STRING.length() * Math.random());

            randomString.append(ALPHA_NUMERIC_STRING.charAt(index));
        }

        return randomString.toString();
    }

    public static String generateRobotsFile(String url) {
        return String.format(ROBOTS, url);
    }
}
