package xyz.jcpalma.taskcli.commands;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.*;
import xyz.jcpalma.taskcli.helpers.ShellHelper;
import xyz.jcpalma.taskcli.models.Task;
import xyz.jcpalma.taskcli.services.TaskService;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@ShellComponent
public class TaskCommand {

    private final TaskService taskService;

    private final ShellHelper shellHelper;

    public TaskCommand(TaskService taskService, ShellHelper shellHelper) {
        this.taskService = taskService;
        this.shellHelper = shellHelper;
    }

    @ShellMethod("List all tasks")
    public List<Task> list() {
        return taskService.findAll();
    }

    @ShellMethod("Print a task by id")
    public void printTask(@ShellOption({"-i", "--id"}) Long id) {
        Optional<Task> optionalTask = taskService.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();

            Object[][] data = new Object[][]{
                {"Id", task.getId()},
                {"Title", task.getTitle()},
                {"Description", task.getDescription()},
                {"Completed", task.getCompleted()}
            };

            TableModel model = new ArrayTableModel(data);
            TableBuilder tableBuilder = new TableBuilder(model);
            tableBuilder.addFullBorder(BorderStyle.fancy_light);
            System.out.println(tableBuilder.build().render(80));

        } else {
            shellHelper.printError("Task not found\n");
        }
    }

    @ShellMethod("List all tasks in a table.")
    public void printTasks(
        @ShellOption(value = {"-p"}, defaultValue = "0", help = "Number of page between 1 to n.") Integer page,
        @ShellOption(value = {"-s"}, defaultValue = "5", help = "Positive number for size of page.") Integer size
    ) {

        Iterable<Task> tasks = page > 0
            ? taskService.findAll(PageRequest.of(page - 1, size))
            : taskService.findAll();

        final TableModel model = new BeanListTableModel<>(tasks, getHeaders());
        final TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addFullBorder(BorderStyle.fancy_light);
        String content = tableBuilder.build().render(80);
        System.out.print(content);
        printFooter(tasks, content.indexOf("\n"));
    }

    private LinkedHashMap<String, Object> getHeaders() {
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("id", "ID");
        headers.put("title", "Title");
        headers.put("description", "Description");
        headers.put("completed", "Completed");
        return headers;
    }

    private void printFooter(Iterable<Task> tasks, int width) {

        String ps = "";
        Object[] total;

        if (tasks instanceof Page) {
            Page<Task> page = (Page<Task>) tasks;
            ps = String.format(" Page %d of %d ", page.getNumber() + 1, page.getTotalPages());
            total = new Object[]{page.getTotalElements()};
        } else {
            total = new Object[]{((List<Task>) tasks).size()};
        }

        MessageFormat mf = new MessageFormat("({0, choice, 0#no tasks|1#1 task|1<{0} tasks})");
        System.out.printf("%s%" + (width - ps.length()) + "s%n%n", ps, mf.format(total));
    }

}
