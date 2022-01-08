package xyz.jcpalma.taskcli.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import xyz.jcpalma.taskcli.models.Task;

public interface ITaskRepository extends PagingAndSortingRepository<Task, Long> {
}
