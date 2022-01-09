package xyz.jcpalma.taskcli.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import xyz.jcpalma.taskcli.models.Task;

import java.util.List;
import java.util.Optional;

public interface ITaskService {

    List<Task> findAll();

    Page<Task> findAll(Pageable pageable);

    Page<Task> findByCompleted(Pageable pageable);

    Optional<Task> findById(Long id);

    Optional<Task> save(Task task);

    Optional<Task> deleteById(Long id);

    void delete(Task task);

}
