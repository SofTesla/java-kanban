package Managers;

import Entities.Epic;
import Entities.SubTask;
import Entities.Task;
import Entities.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;

import static Entities.TaskStatus.NEW;

public class InMemoryTaskManager implements TaskManager {
    int lastId;
    HashMap<Integer, Task> tasks;
    HashMap<Integer, Epic> epics;
    HashMap<Integer, SubTask> subTasks;
    HistoryManager historyManager;

    public InMemoryTaskManager(HistoryManager historyManager) {
        lastId = 0;
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subTasks = new HashMap<>();
        this.historyManager = historyManager;
    }

    @Override
    public int getNewId() {
        return ++lastId;
    }

    @Override
    public int addTask(Task task) {
        task.setId(getNewId());
        task.setStatus(NEW);
        tasks.put(task.getId(), task);


        return task.getId();
    }

    @Override
    public int addEpic(Epic epic) {
        epic.setId(getNewId());

        epics.put(epic.getId(), epic);

        return epic.getId();
    }

    @Override
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

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        historyManager.add(task);
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public SubTask getSubTaskById(int id) {
        SubTask subTask = subTasks.get(id);
        historyManager.add(subTask);
        return subTask;
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        subTasks.clear();
        epics.clear();
    }

    @Override
    public void deleteAllSubTasks() {
        subTasks.clear();

        for (Epic epic : epics.values()) {
            epic.getSubTasks().clear();
            updateEpicStatus(epic);
        }
    }

    @Override
    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    @Override
    public void deleteEpicById(int id) {
        Epic epic = epics.get(id);

        for (Integer subTaskId : epic.getSubTasks().keySet()) {
            deleteSubTaskById(subTaskId);
        }

        epics.remove(id);
    }

    @Override
    public void deleteSubTaskById(int id) {
        Epic epic = subTasks.get(id).getEpic();
        epic.getSubTasks().remove(id);
        subTasks.remove(id);
        updateEpicStatus(epic);
    }

    @Override
    public void replaceTaskById(int id, Task task) {
        tasks.put(id, task);
    }

    @Override
    public void replaceEpicById(int id, Epic epic) {
        epics.put(id, epic);
    }

    @Override
    public void replaceSubTaskById(int id, SubTask subTask) {
        SubTask oldSubTask =  subTasks.get(id);
        subTask.setEpic(oldSubTask.getEpic());
        subTask.getEpic().getSubTasks().put(id, subTask);
        subTasks.put(id, subTask);
        updateEpicStatus(subTask.getEpic());
    }

    @Override
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
