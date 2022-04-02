package com.example.demo.telegram.operations;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Сервис обычных операций
 */
@Service
public class RegularOperations {
    /**
     * Операция старта
     *
     * @param message - пришедшее сообщение
     * @return ответ
     */
    public String start(Message message) {
        return "Привет, " + message.getFrom().getFirstName() + "!\nВы написали:\n" + message.getText();
    }
}
