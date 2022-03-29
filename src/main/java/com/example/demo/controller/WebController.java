package com.example.demo.controller;

import com.example.demo.entities.Tasks;
import com.example.demo.form.TaskForm;
import com.example.demo.service.TasksService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * Контроллер веб-страниц
 */
@Controller
@Log
public class WebController {
    /**
     * Сообщение на главной странице
     */
    private static final String MAIN_PAGE_MESSAGE = "Это главная страница";
    /**
     * Заголовок главной страницы
     */
    private static final String MAIN_PAGE_TITLE = "Здравствуйте!";
    /**
     * Сообщение на странице списка
     */
    private static final String LIST_PAGE_MESSAGE = "Это страница списка";
    /**
     * Заголовок на странице списка
     */
    private static final String LIST_PAGE_TITLE = "Список задач";
    /**
     * Заголовок на странице списка
     */
    private static final String EDIT_PAGE_TITLE = "Редактирование задачи";

    /**
     * Сервис задач
     */
    private final TasksService tasksService;

    /**
     * Конструктор контроллера задач
     *
     * @param tasksService сервис задач
     */
    @Autowired
    public WebController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    /**
     * Cтраница списка задач доступна по адресу `taskList`,
     *
     * @param model - модель
     * @return - возвращает путь к шаблону
     */
    @GetMapping(value = {"/taskList"})
    public String tasksList(Model model) {
        // читаем все задачи
        final List<Tasks> tasks = tasksService.readAll();
        // добавляем их в список
        model.addAttribute("tasks", tasks);
        // задаём сообщение
        model.addAttribute("message", LIST_PAGE_MESSAGE);
        // задаём заголовок
        model.addAttribute("title", LIST_PAGE_TITLE);
        // добавляем фому
        TaskForm taskForm = new TaskForm();
        model.addAttribute("taskForm", taskForm);
        return "taskList";
    }

    /**
     * Посмотреть задачу
     *
     * @param model - модель
     * @return - возвращает путь к шаблону
     */
    @GetMapping(value = {"/tasks/{id}"})
    public String showTask(Model model, @PathVariable(name = "id") int id) {
        // читаем все задачи
        Tasks task = tasksService.read(id);
        // задаём сообщение
        model.addAttribute("message", "Задача: " + id);
        // задаём заголовок
        model.addAttribute("title", EDIT_PAGE_TITLE);
        // добавляем фому
        TaskForm taskForm = new TaskForm();
        taskForm.setText(task.getText());
        taskForm.setAuthor(task.getAuthor());
        taskForm.setTitle(task.getTitle());
        model.addAttribute("taskForm", taskForm);
        model.addAttribute("taskId", id);
        return "taskEdit";
    }

    /**
     * Изменить задачу
     *
     * @param model    - модель
     * @param taskForm - форма с задачей
     * @return - возвращает путь к шаблону
     */
    @PostMapping(value = {"/tasks/{id}"})
    public String saveTask(Model model, @ModelAttribute("taskForm") TaskForm taskForm,
                           @PathVariable(name = "id") int id) {
        // получаем значения из формы
        String author = taskForm.getAuthor();
        String text = taskForm.getText();
        String title = taskForm.getTitle();

        // если все элементы формы получены и непустые
        if (author != null && author.length() > 0 && text != null && text.length() > 0 && title != null && title.length() > 0) {
            // создаём новую задачу
            Tasks task = new Tasks();
            task.setAuthor(author);
            task.setText(text);
            task.setTitle(title);
            // добавляем задачу в БД
            tasksService.update(task, id);
            // переходим к списку задач
            return "redirect:/taskList";
        }

        model.addAttribute("errorMessage", "Форма заполнена некорректно");
        return "redirect:/taskList";
    }

    /**
     * Добавить задачу
     *
     * @param model    - модель
     * @param taskForm - форма с новой задачей
     * @return - возвращает путь к шаблону
     */
    @PostMapping(value = {"/addTask"})
    public String savePerson(Model model, @ModelAttribute("taskForm") TaskForm taskForm) {
        // получаем значения из формы
        String author = taskForm.getAuthor();
        String text = taskForm.getText();
        String title = taskForm.getTitle();

        // если все элементы формы получены и непустые
        if (author != null && author.length() > 0 && text != null && text.length() > 0 && title != null && title.length() > 0) {
            // создаём новую задачу
            Tasks task = new Tasks();
            task.setAuthor(author);
            task.setText(text);
            task.setTitle(title);
            // добавляем задачу в БД
            tasksService.create(task);
            // переходим к списку задач
            return "redirect:/taskList";
        }

        model.addAttribute("errorMessage", "Форма заполнена некорректно");
        return "redirect:/taskList";
    }

    /**
     * Главная страница доступна по адресам `/` и `/index`,
     *
     * @param model - модель
     * @return - возвращает путь к шаблону
     */
    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {
        // задаём сообщение
        model.addAttribute("message", MAIN_PAGE_MESSAGE);
        // задаём заголовок
        model.addAttribute("title", MAIN_PAGE_TITLE);
        // возвращаем шаблон главной страницы
        return "index";
    }

}
