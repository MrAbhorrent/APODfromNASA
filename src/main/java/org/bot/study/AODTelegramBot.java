package org.bot.study;

import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class AODTelegramBot extends TelegramLongPollingBot {

    private final String botName;
    private final String botToken;
    private final String urlSite;


    public AODTelegramBot(String botName, String botToken, String url) {
        this.botName = botName;
        this.botToken = botToken;
        this.urlSite = url;
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        // TODO
        if (update.hasMessage() && update.getMessage().hasText()) {
            /**
             *  /help - получить справку по боту
             *  /image - получить картинку дня
             *  /date YYYY-MM-DD
             */
            String urlImage;
            String[] separatedCommand = update.getMessage().getText().split(" ");
            String action = separatedCommand[0];

            long chatId = update.getMessage().getChatId();
            switch (action) {
                case "/start":
                case "Старт":
                    sendMessage("Привет! Я бот который загружает картинку дня с сайта NASA", chatId);
                    break;
                case "/help":
                case "Помощь":
                    String message = "Это бот отправки картинок дня с сайта NASA.\n" +
                            "\tДля получения картинки отправьте команду /image\n" +
                            "\tДля получения картинки за определенный день отправьте команду /data и укажите дату в формате YYYY-MM-DD";
                    sendMessage(message, chatId);
                    break;
                case "/image":
                case "Загрузить":
                    System.out.println(urlSite);
                    urlImage = APODUtils.getUrlAPOD(urlSite);
                    System.out.printf("URL картинки - %s%n", urlImage);
                    sendMessage(urlImage, chatId);
                    break;
                case "/date":
                case "Введите":
                    if (separatedCommand.length > 1) {
                        urlImage = APODUtils.getUrlAPOD(urlSite + "&date=" + separatedCommand[1]);
                    } else {
                        urlImage = APODUtils.getUrlAPOD(urlSite);
                    }
                    System.out.printf("URL картинки - %s%n", urlImage);
                    sendMessage(urlImage, chatId);
                    break;
                default:
                    sendMessage("Неизвестная команда. Для справки введите /help", chatId);
            }
        }
    }

    private void sendMessage(String msg, long chatId) {
        SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        message.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add("Старт");
        keyboardFirstRow.add("Помощь");
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add("Загрузить изображение");
        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardSecondRow.add("Введите дату");

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardThirdRow);
        replyKeyboardMarkup.setKeyboard(keyboard);

        message.setChatId(chatId);
        message.setText(msg);

        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            System.out.println("Ошибка обработки сообщения. " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        // TODO
        return this.botName;
    }

    @Override
    public String getBotToken() {
        // TODO
        return this.botToken;
    }
}
