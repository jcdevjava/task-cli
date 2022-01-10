package xyz.jcpalma.taskcli.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.jcpalma.taskcli.models.Task;
import xyz.jcpalma.taskcli.repositories.ITaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService implements ITaskService {

    private final ITaskRepository repository;

    public TaskService(ITaskRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findAll() {
        return (List<Task>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Task> findByCompleted(Pageable pageable, boolean completed) {
        return completed
            ? repository.findByCompletedTrue(pageable)
            : repository.findByCompletedFalse(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Task> findByTitleOrDescription(String search, Pageable pageable){
        return repository.findByTitleContainsIgnoreCaseOrDescriptionContainsIgnoreCase(pageable, search, search);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Task> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Task> findById(Long id) {
        return id != null ? repository.findById(id) : Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Task> save(Task task) {
        return Optional.of(repository.save(task));
    }

    @Override
    @Transactional
    public Optional<Task> deleteById(Long id) {
        Optional<Task> task = findById(id);
        if (task.isPresent()) {
            repository.deleteById(id);
        }
        return task;
    }

    @Override
    @Transactional
    public void delete(Task task) {
        repository.delete(task);
    }
}
