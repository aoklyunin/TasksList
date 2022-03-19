package com.example.demo.service;

import com.example.demo.dto.TasksDto;
import com.example.demo.entities.Tasks;
import com.example.demo.repositories.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Objects.isNull;

@AllArgsConstructor
@Service
public class DefaultTasksService implements TasksService{

    // Хранилище клиентов
    private static final Map<Integer, Tasks> Task_REPOSITORY_MAP = new HashMap<>();

    // Переменная для генерации ID клиента
    private static final AtomicInteger Task_ID_HOLDER = new AtomicInteger();

    @Override
    public void create(Tasks task) {
        final int TaskId = Task_ID_HOLDER.incrementAndGet();
        task.setId(TaskId);
        Task_REPOSITORY_MAP.put(TaskId, task);
    }

    @Override
    public List<Tasks> readAll() {
        return new ArrayList<>(Task_REPOSITORY_MAP.values());
    }

    @Override
    public Tasks read(int id) {
        return Task_REPOSITORY_MAP.get(id);
    }

    @Override
    public boolean update(Tasks task, int id) {
        if (Task_REPOSITORY_MAP.containsKey(id)) {
            task.setId(id);
            Task_REPOSITORY_MAP.put(id, task);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(int id) {
        return Task_REPOSITORY_MAP.remove(id) != null;
    }


}
