import Entities.Epic;
import Entities.SubTask;
import Entities.TaskStatus;
import Managers.TaskManager;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Epic epic = new Epic("Эпик 1", "Описание");
        SubTask subTask = new SubTask("Сабтаск 1", "Описание", TaskStatus.NEW);
        Epic epic2 = new Epic("Эпик 2", "Описание");
        SubTask subTask2 = new SubTask("Сабтаск еще", "Описание", TaskStatus.NEW);
        SubTask subTask3 = new SubTask("Сабтаск 3", "Описание", TaskStatus.NEW);

        int epicId = taskManager.addEpic(epic);
        taskManager.addSubTask(subTask, epicId);
        int epic2Id = taskManager.addEpic(epic2);
        taskManager.addSubTask(subTask2, epic2Id);
        int subTask3Id = taskManager.addSubTask(subTask3, epic2Id);
        subTask3.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.replaceSubTaskById(subTask3Id, subTask3);

        System.out.println(taskManager.getAllEpics());
    }
}
