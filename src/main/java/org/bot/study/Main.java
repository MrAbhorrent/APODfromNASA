package org.bot.study;

public class Main {
    private static final String WELCOME = "-= Программа получения картинки дня с сайта NASA =-";
    private static final String FILE_WITH_KEY = "api.key";
    private static final String BOT_NAME = "MyTestJustBot_bot";

    public static void main(String[] args) {
        System.out.println(WELCOME);
        String config = readConfig(FILE_WITH_KEY);
        String apiKey = readKey(config, "API_KEY");
        String telegramBotToken = readKey(config, "TELEGRAM_BOT_TOKEN");
        String url = String.format("https://api.nasa.gov/planetary/apod?api_key=%s", apiKey);

        //AODTelegramBot telegramBot = new AODTelegramBot(BOT_NAME, TELEGRAM_BOT_KEY, url);
        new AODTelegramBot(BOT_NAME, telegramBotToken, url);
    }

    private static String readConfig(String configFile) {

        return Utils.readFile(configFile);
    }

    private static String readKey(String _contentFile, String parameter) {

        //TODO: Необходимо проверить что мы действительно прочитали ключ
        String[] contentFileLine = _contentFile.split(";");
        for (String str : contentFileLine) {
            if (str.length() > 0) {
                String[] row = str.split("=");
                if (row[0].equals(parameter)) {
                    return  row[1];
                }
            }
        }
        return null;
    }
}
