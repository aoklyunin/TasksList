package com.example.demo.telegram.operations;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class RegularOperations {
    public String start(Message message) {
        return "Привет, " + message.getFrom().getFirstName() + "!\nВы написали:\n" + message.getText();
    }
}
