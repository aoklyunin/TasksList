package com.example.demo.telegram.operations;

import com.example.demo.entities.Tasks;
import com.example.demo.entities.User;
import com.example.demo.service.TasksService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

/**
 * Сервис обычных операций
 */
@Service
@Log
public class RegularOperations {
    /**
     * Сервис задач
     */
    TasksService tasksService;

    /**
     * Конструктор сервиса обычных операций
     *
     * @param tasksService - сервис задач
     */
    @Autowired
    RegularOperations(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    /**
     * Операция старта
     *
     * @param message - пришедшее сообщение
     * @return ответ
     */
    public String hello(Message message) {
        return "Привет, " + message.getFrom().getFirstName() + "!\n" +
                "Вы написали:\n" + message.getText();
    }

    /**
     * Добавить задание
     *
     * @param message       сообщение с текстом
     * @param connectedUser подключенный пользователь
     * @return ответ
     */
    public String addTask(Message message, User connectedUser) {
        Tasks task = new Tasks();
        task.setTitle("Задача от бота");
        task.setAuthor(connectedUser);
        task.setText(message.getText());
        tasksService.create(task);
        return "Задача добавлена";
    }

    /**
     * Отобразить список заданий
     *
     * @param connectedUser подключенный пользователь
     * @return ответ
     */
    public String listTask(User connectedUser) {
        String result = "Задачи:\n\n";
        // получаем список задачам конкретного пользователя
        List<Tasks> tasksList = tasksService.readAll().stream().filter(
                t -> t.getAuthor().getId().equals(connectedUser.getId())
        ).toList();
        // формируем строку вывода
        for (Tasks task : tasksList) {
            result += ">" + task.getTitle() + "\n" +
                    task.getText() + "\n\n";
        }
        result +="\n";
        return result;
    }

}
