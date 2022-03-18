package com.example.demo.service;


import com.example.demo.dto.TasksDto;
import com.example.demo.entities.Tasks;
import org.springframework.scheduling.config.Task;

import java.util.List;

public interface TasksService {

    /**
     * Создает нового клиента
     * @param client - клиент для создания
     */
    void create(Tasks client);

    /**
     * Возвращает список всех имеющихся клиентов
     * @return список клиентов
     */
    List<Tasks> readAll();

    /**
     * Возвращает клиента по его ID
     * @param id - ID клиента
     * @return - объект клиента с заданным ID
     */
    Tasks read(int id);

    /**
     * Обновляет клиента с заданным ID,
     * в соответствии с переданным клиентом
     * @param client - клиент в соответсвии с которым нужно обновить данные
     * @param id - id клиента которого нужно обновить
     * @return - true если данные были обновлены, иначе false
     */
    boolean update(Tasks client, int id);

    /**
     * Удаляет клиента с заданным ID
     * @param id - id клиента, которого нужно удалить
     * @return - true если клиент был удален, иначе false
     */
    boolean delete(int id);
}