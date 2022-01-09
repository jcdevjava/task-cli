package xyz.jcpalma.taskcli.commands;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.*;
import xyz.jcpalma.taskcli.helpers.InputReader;
import xyz.jcpalma.taskcli.helpers.ShellHelper;
import xyz.jcpalma.taskcli.models.Task;
import xyz.jcpalma.taskcli.services.TaskService;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ShellComponent
public class TaskCommand {

    private final TaskService taskService;

    private final ShellHelper shellHelper;

    private final InputReader in;

    public TaskCommand(
        TaskService taskService,
        ShellHelper shellHelper,
        InputReader inputReader
    ) {
        this.taskService = taskService;
        this.shellHelper = shellHelper;
        this.in = inputReader;
    }

    @ShellMethod(value = "Complete a task by id", key = {"done", "complete"})
    public void completeTask(@ShellOption({"-t", "--task", "--id"}) Long id) {
        Optional<Task> optionalTask = taskService.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setCompleted(true);
            taskService.save(task);
            shellHelper.printSuccess(String.format("Task '%s' completed! ✓", task.getTitle()));
        } else {
            shellHelper.printError("Error: Task not found!");
        }
        System.out.println();
    }

    @ShellMethod(value = "Remove a task by id.", key = {"remove", "rm"})
    public void removeTask(@ShellOption({"-t", "--task", "--id"}) Long id) {
        Optional<Task> optionalTask = taskService.findById(id);

        if(optionalTask.isPresent()) {
            Task task = optionalTask.get();
            printTask(task);
            String result = in.read(" Are you sure you want to remove this task? (y/n): ");
            if( result.equals("y") ) {
                taskService.delete(task);
                shellHelper.printSuccess(String.format(" Task '%s' deleted!", optionalTask.get().getTitle()));
            }else  {
                shellHelper.printWarning(" Task not removed!");
            }
        }else {
            shellHelper.printError("Error: Task not found!");
        }
        System.out.println();
    }

    @ShellMethod("List all tasks")
    public List<Task> list() {
        return taskService.findAll();
    }

    @ShellMethod(value = "Print a task by id", key = {"task", "show"})
    public void printTask(@ShellOption({"-t", "--task", "--id"}) Long id) {
        Optional<Task> optionalTask = taskService.findById(id);
        if (optionalTask.isPresent()) {
            printTask(optionalTask.get());
        } else {
            shellHelper.printError("Task not found\n");
        }
    }

    @ShellMethod("List all tasks in a table.")
    public void printTasks(
        @ShellOption(value = {"-p", "--page"}, defaultValue = "0", help = "Number of page between 1 to n.") Integer page,
        @ShellOption(value = {"-s", "--size"}, defaultValue = "5", help = "Positive number for size of page.") Integer size,
        @ShellOption(value = {"-c", "--completed"}, arity = 0, defaultValue = "false", help = "Filter by completed.") Boolean completed
    ) {

        Iterable<Task> tasks = page > 0
            ? completed
                ? taskService.findByCompleted(PageRequest.of(page - 1, size))
                : taskService.findAll(PageRequest.of(page - 1, size))
            : completed
                ? taskService.findAll().stream().filter(Task::getCompleted).collect(Collectors.toList())
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

    private void printTask(Task task) {
        final Object[][] data = new Object[][]{
            {"Id", task.getId()},
            {"Title", task.getTitle()},
            {"Description", task.getDescription()},
            {"Completed", task.getCompleted()}
        };

        TableModel model = new ArrayTableModel(data);
        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addFullBorder(BorderStyle.fancy_light);
        System.out.println(tableBuilder.build().render(80));
    }

}
