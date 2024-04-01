import Entities.Epic;
import Entities.SubTask;
import Entities.TaskStatus;
import Managers.Managers;
import Managers.TaskManager;


public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic("Эпик 1", "Описание", 1);
        SubTask subTask = new SubTask("Сабтаск 1", "Описание", TaskStatus.NEW, 2);
        Epic epic2 = new Epic("Эпик 2", "Описание", 3);
        SubTask subTask2 = new SubTask("Сабтаск еще", "Описание", TaskStatus.NEW, 4);
        SubTask subTask3 = new SubTask("Сабтаск 3", "Описание", TaskStatus.NEW, 5);

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
