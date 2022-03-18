package com.example.demo.service;


import com.example.demo.dto.TasksDto;
import com.example.demo.entities.Tasks;
import org.springframework.stereotype.Component;

@Component
public class TasksConverter {


    public Tasks fromUserDtoToUser(TasksDto usersDto) {
        Tasks users = new Tasks();
        users.setId(usersDto.getId());
        users.setText(usersDto.getText());
        users.setAuthor(usersDto.getAuthor());
        users.setTitle(usersDto.getTitle());
        return users;
    }

    public TasksDto fromUserToUserDto(Tasks users) {
        return TasksDto.builder()
                .id(users.getId())
                .text(users.getText())
                .author(users.getAuthor())
                .title(users.getTitle())
                .build();
    }
}
