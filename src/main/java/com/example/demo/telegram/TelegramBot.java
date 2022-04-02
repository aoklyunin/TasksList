package com.example.demo.telegram;

import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.service.UserService;
import com.example.demo.telegram.operations.RegularOperations;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramBot extends SpringWebhookBot {
    /**
     * Команда старта
     */
    private static final String COMMAND_ADD_TASK = "/addTask";
    /**
     * Команда старта
     */
    private static final String COMMAND_LIST_TASK = "/listTask";
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
     * Сервис для работы с пользователями
     */
    @Autowired
    UserService userService;
    /**
     * Сервис обычных операций
     */
    @Autowired
    RegularOperations regularOperations;
    /**
     * Объект для работы с паролями
     */
    final BCryptPasswordEncoder bCryptPasswordEncoder;
    /**
     * Состояние бота
     */
    BotState botState;
    /**
     * Имя пользователя для подключения
     */
    User connectedUser;

    /**
     * Конструктор телеграм-бота
     *
     * @param options    - параметры
     * @param setWebhook - бин связывания веб-хуков
     */
    public TelegramBot(DefaultBotOptions options, SetWebhook setWebhook) {
        super(options, setWebhook);
        this.botState = BotState.STATE_START;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Конструктор телеграм-бота
     *
     * @param setWebhook - бин связывания веб-хуков
     */
    public TelegramBot(SetWebhook setWebhook) {
        super(setWebhook);
        this.botState = BotState.STATE_START;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
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
            // если у сообщения есть текст
            if (message.hasText()) {
                // формируем ответ
                SendMessage sendMessage = new SendMessage();
                // переходим в чат с пользователем
                sendMessage.setChatId(String.valueOf(message.getChatId()));
                // задаём текст сообщения
                sendMessage.setText(switch (botState) {
                    case STATE_START -> processStart(message);
                    case STATE_WAIT_FOR_USERNAME -> waitForUsername(message);
                    case STATE_WAIT_FOR_PASSWORD -> waitForPassword(message);
                    case STATE_CONNECTED -> processConnected(message);
                    case STATE_WAIT_FOR_TASK -> waitForTask(message);
                });
                // возвращаем ответное сообщение
                return sendMessage;
            }
        }
        return null;
    }

    /**
     * Обработка начального состояния
     *
     * @param message - сообщение
     * @return ответ
     */
    private String processStart(Message message) {
        try {
            // пытаемся найти сохранённого пользователя в базе
            connectedUser = (User) userService.loadUserByTUsername(message.getFrom().getUserName());
            botState = BotState.STATE_CONNECTED;
            return "Здравствуйте, " + message.getFrom().getFirstName() + ".\n" +
                    "Ваш логин в системе " + connectedUser.getUsername()    ;
        } catch (Exception e) {
            log.info(e.getMessage());
            botState = BotState.STATE_WAIT_FOR_USERNAME;
            return "Вы не авторизованы, введите ваш логин";
        }
    }

    /**
     * Обработка состояния ожидания логина
     *
     * @param message - сообщение
     * @return ответ
     */
    private String waitForUsername(Message message) {
        try {
            connectedUser = (User) userService.loadUserByUsername(message.getText());
            botState = BotState.STATE_WAIT_FOR_PASSWORD;
            return "Введите пароль";
        } catch (Exception e) {
            return "Пользователь с логином " + message.getText() + " не найден\nпопробуйте ещё раз";

        }
    }

    /**
     * Обработка состояния ожидания пароля
     *
     * @param message - сообщение
     * @return ответ
     */
    private String waitForPassword(Message message) {
        if (connectedUser == null)
            return "Ошибка: пользователь не найден";
        // если пароли совпадают
        if (bCryptPasswordEncoder.matches(message.getText(), connectedUser.getPassword())) {
            log.info(userService.saveUser(connectedUser, message.getFrom().getUserName()) + "");
            botState = BotState.STATE_CONNECTED;
            return "Связывание выполнено";
        } else {
            return "Неверный пароль";
        }
    }

    /**
     * Подключённое состояние
     *
     * @param message - сообщение
     * @return ответ
     */
    private String processConnected(Message message) {
        return switch (message.getText()) {
            case COMMAND_ADD_TASK -> doWaitForTask();
            case COMMAND_LIST_TASK -> regularOperations.listTask(connectedUser);
            default -> regularOperations.hello(message);
        };
    }


    /**
     * Обработка состояния ожидания задачи
     *
     * @param message - сообщение
     * @return ответ
     */
    private String waitForTask(Message message) {
        regularOperations.addTask(message, connectedUser);
        botState = BotState.STATE_CONNECTED;
        return "Задача добавлена";
    }



    /**
     * Запустить режим ожидания задачи
     *
     * @return ответ бота
     */
    private String doWaitForTask() {
        botState = BotState.STATE_WAIT_FOR_TASK;
        return "Введите текст задачи";
    }


}
