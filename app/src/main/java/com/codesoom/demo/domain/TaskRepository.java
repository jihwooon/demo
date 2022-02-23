package com.codesoom.demo.domain;

import com.codesoom.demo.TaskNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface TaskRepository {

    List<Task> findAll();

    Optional<Task> findById(Long id);

    Task save(Task source);

    void delete(Task task);

}
