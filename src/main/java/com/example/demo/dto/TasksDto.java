package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TasksDto {

    private Integer id;
    /**
     * Заголовок
     */
    private String title;
    /**
     * Автор
     */
    private String author;
    /**
     * Текст
     */
    private String text;
}