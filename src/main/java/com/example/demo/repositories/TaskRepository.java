package com.example.demo.repositories;

import com.example.demo.entities.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Tasks, Integer> {

    List<Tasks> findAllByAuthor(String author);

}
