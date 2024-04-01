package Managers;

import Entities.Epic;
import Entities.SubTask;
import Entities.Task;

import java.util.ArrayList;

public interface TaskManager {
    int getNewId();

    int addTask(Task task);

    int addEpic(Epic epic);

    int addSubTask(SubTask subTask, int epicId);

    ArrayList<Task> getAllTasks();

    ArrayList<Epic> getAllEpics();

    ArrayList<SubTask> getAllSubTasks();

    Task getTaskById(int id);

    Epic getEpicById(int id);

    SubTask getSubTaskById(int id);

    void deleteAllTasks();

    void deleteAllEpics();

    void deleteAllSubTasks();

    void deleteTaskById(int id);

    void deleteEpicById(int id);

    void deleteSubTaskById(int id);

    void replaceTaskById(int id, Task task);

    void replaceEpicById(int id, Epic epic);

    void replaceSubTaskById(int id, SubTask subTask);

    ArrayList<SubTask> getSubTasksByEpicId(int id);


}
