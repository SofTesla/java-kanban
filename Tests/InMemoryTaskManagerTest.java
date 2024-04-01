import Entities.Epic;
import Entities.SubTask;
import Entities.Task;
import Managers.Managers;
import Managers.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static Entities.TaskStatus.NEW;

public class InMemoryTaskManagerTest {
    @Test
    void addTask() {
        TaskManager taskManager = Managers.getDefault();
        Task task = new Task("Test addTask", "Test addTask description", NEW, 1);
        final int taskId = taskManager.addTask(task);
        final Task savedTask = taskManager.getTaskById(taskId);

        Assertions.assertNotNull(savedTask, "Задача не найдена.");
        Assertions.assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getAllTasks();

        Assertions.assertNotNull(tasks, "Задачи не возвращаются.");
        Assertions.assertEquals(1, tasks.size(), "Неверное количество задач.");
        Assertions.assertEquals(task, tasks.getFirst(), "Задачи не совпадают.");
    }

    @Test
    void addSubTask() {
        TaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic("Test addSubTask", "Test addSubTask description", 1);
        SubTask subTask = new SubTask("Test addSubTask", "Test addSubTask description", NEW, 2);
        final int epicId = taskManager.addEpic(epic);
        final int subTaskId = taskManager.addSubTask(subTask, epicId);
        final SubTask savedSubTask = taskManager.getSubTaskById(subTaskId);

        Assertions.assertNotNull(savedSubTask, "Сабтаск не найден.");
        Assertions.assertEquals(subTask, savedSubTask, "Сабтаски не совпадают.");

        final List<SubTask> subTasks = taskManager.getAllSubTasks();

        Assertions.assertNotNull(subTasks, "Сабтаски не возвращаются.");
        Assertions.assertEquals(1, subTasks.size(), "Неверное количество сабтасков.");
        Assertions.assertEquals(subTask, subTasks.getFirst(), "Сабтаски не совпадают.");
    }

    @Test
    void addEpic() {
        TaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic("Test addEpic", "Test addEpic description", 1);
        final int epicId = taskManager.addEpic(epic);
        final Epic savedEpic = taskManager.getEpicById(epicId);

        Assertions.assertNotNull(savedEpic, "Эпик не найден.");
        Assertions.assertEquals(epic, savedEpic, "Эпики не совпадают.");

        final List<Epic> epics = taskManager.getAllEpics();

        Assertions.assertNotNull(epics, "Эпики не возвращаются.");
        Assertions.assertEquals(1, epics.size(), "Неверное количество эпиков.");
        Assertions.assertEquals(epic, epics.getFirst(), "Эпики не совпадают.");
    }

    @Test
    void replaceTaskById() {
        TaskManager taskManager = Managers.getDefault();
        Task task = new Task("Test replaceTaskById", "Test replaceTaskById description", NEW, 1);
        Task task1 = new Task("Test replaceTaskById 1", "Test replaceTaskById description 1", NEW, 1);
        final int taskId = taskManager.addTask(task);
        taskManager.replaceTaskById(taskId, task1);
        Task replacedTask = taskManager.getTaskById(taskId);
        Assertions.assertEquals(task1, replacedTask);
    }

    @Test
    void replaceEpicById() {
        TaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic("Test replaceEpicById", "Test replaceEpicById description", 1);
        Epic epic1 = new Epic("Test replaceEpicById 1", "Test replaceEpicById description 1", 1);
        final int epicId = taskManager.addEpic(epic);
        taskManager.replaceEpicById(epicId, epic1);
        Epic replacedEpic = taskManager.getEpicById(epicId);
        Assertions.assertEquals(epic1, replacedEpic);
    }

    @Test
    void replaceSubTaskById() {
        TaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic("Test replaceSubTaskById", "Test replaceSubTaskById description", 1);
        SubTask subTask = new SubTask("Test replaceSubTaskById", "Test replaceSubTaskById description", NEW, 2);
        SubTask subTask1 = new SubTask("Test replaceSubTaskById 1", "Test replaceSubTaskById description 1", NEW, 3);
        final int epicId = taskManager.addEpic(epic);
        final int subTaskId = taskManager.addSubTask(subTask, epicId);
        taskManager.replaceSubTaskById(subTaskId, subTask1);
        SubTask replacedSubTask = taskManager.getSubTaskById(subTaskId);
        Assertions.assertEquals(subTask1, replacedSubTask);
    }

    @Test
    void deleteTaskById() {
        TaskManager taskManager = Managers.getDefault();
        Task task = new Task("Test deleteTask", "Test deleteTask description", NEW, 1);
        final int taskId = taskManager.addTask(task);
        taskManager.deleteTaskById(taskId);
        Task deletedTask = taskManager.getTaskById(taskId);
        Assertions.assertNull(deletedTask);
    }

    @Test
    void deleteEpicById() {
        TaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic("Test deleteEpic", "Test deleteEpic description", 1);
        final int epicId = taskManager.addEpic(epic);
        taskManager.deleteEpicById(epicId);
        Task deletedEpic = taskManager.getEpicById(epicId);
        Assertions.assertNull(deletedEpic);
    }

    @Test
    void deleteSubTaskById() {
        TaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic("Test deleteSubTask", "Test deleteSubTask description", 1);
        SubTask subTask = new SubTask("Test deleteSubTask", "Test deleteSubTask description", NEW, 2);
        final int epicId = taskManager.addEpic(epic);
        final int subTaskId = taskManager.addSubTask(subTask, epicId);
        taskManager.deleteSubTaskById(subTaskId);
        Task deletedSubTask = taskManager.getSubTaskById(subTaskId);
        Assertions.assertNull(deletedSubTask);
    }
}
