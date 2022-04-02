package com.example.demo.telegram;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

/**
 * Класс телеграм-бота
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramBot extends SpringWebhookBot {
    /**
     * Пусть к боту
     */
    String botPath;
    /**
     * username бота
     */
    String botUsername;
    /**
     * Токен бота
     */
    String botToken;

    /**
     * Конструктор телеграм-бота
     *
     * @param options    - параметры
     * @param setWebhook - бин связывания веб-хуков
     */
    public TelegramBot(DefaultBotOptions options, SetWebhook setWebhook) {
        super(options, setWebhook);
    }

    /**
     * Конструктор телеграм-бота
     *
     * @param setWebhook - бин связывания веб-хуков
     */
    public TelegramBot(SetWebhook setWebhook) {
        super(setWebhook);
    }

    /**
     * Обработчик запросов от телеграм-сервера
     *
     * @param update - объект запроса
     * @return - объект запроса API телеграма
     */
    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        // Если в запросе есть `Callback`
        if (update.hasCallbackQuery()) {
            // пока что просто получаем его объект
            CallbackQuery callbackQuery = update.getCallbackQuery();
            return null;
        } else {
            // получаем сообщение
            Message message = update.getMessage();
            // получаем имя пользователя
            String telegramUserName = message.getFrom().getFirstName();
            // если у сообщения есть текст
            if (message.hasText()) {
                // формируем ответ
                SendMessage sendMessage = new SendMessage();
                // переходим в чат с пользователем
                sendMessage.setChatId(String.valueOf(message.getChatId()));
                // задаём текст сообщения
                sendMessage.setText("Привет, " + telegramUserName + "!\nВы написали:\n" + message.getText());
                // возвращаем ответное сообщение
                return sendMessage;
            }
        }
        return null;
    }
}
