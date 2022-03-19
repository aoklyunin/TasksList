package com.example.demo.controller;

import com.example.demo.entities.Tasks;
import com.example.demo.service.TasksService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
@RestController
@RequestMapping("/tasks")
@Log
@CrossOrigin
public class TasksController {

    // Переменная для генерации ID клиента
    private static final AtomicInteger counter = new AtomicInteger();

    private final TasksService clientService;

    @Autowired
    public TasksController(TasksService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/greeting")
    public Tasks greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Tasks(counter.incrementAndGet(), String.format("Hello, %s!", name),
                "", "");
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody Tasks tasks) {
        clientService.create(tasks);
        log.info("sssssssssss");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<Tasks>> read() {
        final List<Tasks> clients = clientService.readAll();

        return clients != null &&  !clients.isEmpty()
                ? new ResponseEntity<>(clients, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/list/{id}")
    public ResponseEntity<Tasks> read(@PathVariable(name = "id") int id) {
        final Tasks client = clientService.read(id);

        return client != null
                ? new ResponseEntity<>(client, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
