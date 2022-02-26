package com.codesoom.demo.infra;

import com.codesoom.demo.domain.Product;
import com.codesoom.demo.domain.ProductRepository;
import com.codesoom.demo.domain.Task;
import com.codesoom.demo.domain.TaskRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

public interface JpaTaskRepository
    extends TaskRepository, CrudRepository<Task, Long>{

    List<Task> findAll();

    Optional<Task> findById(Long id);

    Product save(Product product);

    void delete(Task task);


}
