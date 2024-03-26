package Managers;

import Entities.Epic;
import Entities.SubTask;
import Entities.Task;
import Entities.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;

import static Entities.TaskStatus.NEW;

public class TaskManager {
    int lastId;
    HashMap<Integer, Task> tasks;
    HashMap<Integer, Epic> epics;
    HashMap<Integer, SubTask> subTasks;

    public TaskManager() {
        lastId = 0;
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subTasks = new HashMap<>();
    }

    public int getNewId() {
        return ++lastId;
    }

    public int addTask(Task task) {
        task.setId(getNewId());
        task.setStatus(NEW);
        tasks.put(task.getId(), task);


        return task.getId();
    }

    public int addEpic(Epic epic) {
        epic.setId(getNewId());

        epics.put(epic.getId(), epic);

        return epic.getId();
    }

    public int addSubTask(SubTask subTask, int epicId) {
        Epic epic = epics.get(epicId);
        subTask.setStatus(NEW);
        subTask.setId(getNewId());
        subTask.setEpic(epic);
        epic.getSubTasks().put(subTask.getId(), subTask);
        subTasks.put(subTask.getId(), subTask);

        updateEpicStatus(subTask.getEpic());

        return subTask.getId();
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    public SubTask getSubTaskById(int id) {
        return subTasks.get(id);
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllEpics() {
        subTasks.clear();
        epics.clear();
    }

    public void deleteAllSubTasks() {
        subTasks.clear();

        for (Epic epic : epics.values()) {
            epic.getSubTasks().clear();
            updateEpicStatus(epic);
        }
    }

    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    public void deleteEpicById(int id) {
        Epic epic = epics.get(id);

        for (Integer subTaskId : epic.getSubTasks().keySet()) {
            deleteSubTaskById(subTaskId);
        }

        epics.remove(id);
    }

    public void deleteSubTaskById(int id) {
        Epic epic = subTasks.get(id).getEpic();
        updateEpicStatus(epic);
        epic.getSubTasks().remove(id);
        subTasks.remove(id);
    }

    public void replaceTaskById(int id, Task task) {
        tasks.put(id, task);
    }

    public void replaceEpicById(int id, Epic epic) {
        epics.put(id, epic);
    }

    public void replaceSubTaskById(int id, SubTask subTask) {
        SubTask oldSubTask =  subTasks.get(id);
        subTask.setEpic(oldSubTask.getEpic());
        subTask.getEpic().getSubTasks().put(id, subTask);
        subTasks.put(id, subTask);
        updateEpicStatus(subTask.getEpic());
    }

    public ArrayList<SubTask> getSubTasksByEpicId(int id) {
        Epic epic = epics.get(id);
        return new ArrayList<>(epic.getSubTasks().values());
    }

    private void updateEpicStatus(Epic epic) {
        ArrayList<SubTask> subTasks = new ArrayList<>(epic.getSubTasks().values());

        if (subTasks.isEmpty()) {
            epic.setStatus(NEW);
            return;
        }

        int newCount = 0;
        int doneCount = 0;

        for (SubTask subTask : subTasks) {
            switch (subTask.getStatus()) {
                case NEW:
                    newCount++;
                    break;
                case TaskStatus.DONE:
                    doneCount++;
                    break;
            }
        }

        if (subTasks.size() == newCount) {
            epic.setStatus(TaskStatus.NEW);
        } else if (subTasks.size() == doneCount) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }


}
