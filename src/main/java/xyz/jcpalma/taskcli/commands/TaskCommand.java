package xyz.jcpalma.taskcli.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.*;
import xyz.jcpalma.taskcli.models.Task;
import xyz.jcpalma.taskcli.services.TaskService;

import java.util.LinkedHashMap;
import java.util.List;

@ShellComponent
public class TaskCommand {

    @Autowired
    private TaskService taskService;

    @ShellMethod("List all tasks")
    public List<Task> list() {
        return taskService.findAll();
    }

    @ShellMethod("List all tasks in a table")
    public Table printTasks(@ShellOption(value={"-p","--page"}, defaultValue = "-1") Integer page) {

        final List<Task> tasks = taskService.findAll();
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("id", "ID");
        headers.put("title", "Title");
        headers.put("description", "Description");
        headers.put("completed", "Completed");


        final TableModel model = new BeanListTableModel<>(tasks, headers);
        final TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addFullBorder(BorderStyle.fancy_light);
        return tableBuilder.build();
    }

}
