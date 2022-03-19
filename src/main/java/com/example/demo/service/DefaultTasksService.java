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
public class DefaultTasksService implements TasksService{
    /**
     *  Хранилище задач
     */
    private static final Map<Integer, Tasks> TASK_REPOSITORY_MAP = new HashMap<>();

    /**
     * Генератор id
     */
    private static final AtomicInteger TASK_ID_HOLDER = new AtomicInteger();

    @Override
    public void create(Tasks task) {
        final int TaskId = TASK_ID_HOLDER.incrementAndGet();
        task.setId(TaskId);
        TASK_REPOSITORY_MAP.put(TaskId, task);
    }

    @Override
    public List<Tasks> readAll() {
        return new ArrayList<>(TASK_REPOSITORY_MAP.values());
    }

    @Override
    public Tasks read(int id) {
        return TASK_REPOSITORY_MAP.get(id);
    }

    @Override
    public boolean update(Tasks task, int id) {
        if (TASK_REPOSITORY_MAP.containsKey(id)) {
            task.setId(id);
            TASK_REPOSITORY_MAP.put(id, task);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(int id) {
        return TASK_REPOSITORY_MAP.remove(id) != null;
    }


}
