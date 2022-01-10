package xyz.jcpalma.taskcli.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import xyz.jcpalma.taskcli.models.Task;

public interface ITaskRepository extends PagingAndSortingRepository<Task, Long> {

    Page<Task> findByCompletedTrue(Pageable pageable);

    Page<Task> findByTitleContainsIgnoreCaseOrDescriptionContainsIgnoreCase(Pageable pageable, String title, String description);

}
