package com.example.demo.service;

import com.example.demo.entities.Tasks;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Сервис задачи по умолчанию
 */
@AllArgsConstructor
@Service
public class DefaultTasksService implements TasksService {
    /**
     * Хранилище задач
     */
    private static final Map<Integer, Tasks> TASK_REPOSITORY_MAP = new HashMap<>();

    /**
     * Генератор id
     */
    private static final AtomicInteger TASK_ID_GENERATOR = new AtomicInteger();

    /**
     * Создает новую задачу
     *
     * @param task - задача для создания
     */
    @Override
    public void create(Tasks task) {
        final int TaskId = TASK_ID_GENERATOR.incrementAndGet();
        task.setId(TaskId);
        TASK_REPOSITORY_MAP.put(TaskId, task);
    }

    /**
     * Возвращает список всех имеющихся задач
     *
     * @return список задач
     */
    @Override
    public List<Tasks> readAll() {
        return new ArrayList<>(TASK_REPOSITORY_MAP.values());
    }

    /**
     * Возвращает задачу по её ID
     *
     * @param id - ID задачи
     * @return - объект задачи с заданным ID
     */
    @Override
    public Tasks read(int id) {
        return TASK_REPOSITORY_MAP.get(id);
    }

    /**
     * Обновляет задачу с заданным ID,
     * в соответствии с переданной задачей
     *
     * @param task - задача в соответсвии с которой нужно обновить данные
     * @param id   - id задачи которого нужно обновить
     * @return - true если данные были обновлены, иначе false
     */
    @Override
    public boolean update(Tasks task, int id) {
        // если в словаре есть указанный `id`
        if (TASK_REPOSITORY_MAP.containsKey(id)) {
            // задаём задаче этот id
            task.setId(id);
            // помещаем новую задачу в словарь
            TASK_REPOSITORY_MAP.put(id, task);
            return true;
        }

        return false;
    }

    /**
     * Удаляет задачу с заданным ID
     *
     * @param id - id задачи, которую нужно удалить
     * @return - true если клиент был удален, иначе false
     */
    @Override
    public boolean delete(int id) {
        return TASK_REPOSITORY_MAP.remove(id) != null;
    }


}
