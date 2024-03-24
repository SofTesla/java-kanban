import java.util.ArrayList;
import java.util.HashMap;

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
        task.id = getNewId();
        task.status = TaskStatus.NEW;
        tasks.put(task.id, task);


        return task.id;
    }

    public int addEpic(Epic epic) {
        epic.id = getNewId();

        epics.put(epic.id, epic);

        return epic.id;
    }

    public int addSubTask(SubTask subTask, int epicId) {
        Epic epic = epics.get(epicId);
        subTask.status = TaskStatus.NEW;
        subTask.id = getNewId();
        subTask.epic = epic;
        epic.subTasks.put(subTask.id, subTask);
        subTasks.put(subTask.id, subTask);

        updateEpicStatus(subTask.epic);

        return subTask.id;
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
            updateEpicStatus(epic);
        }
    }

    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    public void deleteEpicById(int id) {
        Epic epic = epics.get(id);

        for (Integer subTaskId : epic.subTasks.keySet()) {
            deleteSubTaskById(subTaskId);
        }

        epics.remove(id);
    }

    public void deleteSubTaskById(int id) {
        updateEpicStatus(subTasks.get(id).epic);

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
        subTask.epic = oldSubTask.epic;
        subTask.epic.subTasks.put(id, subTask);
        subTasks.put(id, subTask);
        updateEpicStatus(subTask.epic);
    }

    public ArrayList<SubTask> getSubTasksByEpicId(int id) {
        Epic epic = epics.get(id);
        return new ArrayList<>(epic.subTasks.values());
    }

    private void updateEpicStatus(Epic epic) {
        ArrayList<SubTask> subTasks = new ArrayList<>(epic.subTasks.values());

        if (subTasks.isEmpty()) {
            epic.status = TaskStatus.NEW;
            return;
        }

        int newCount = 0;
        int doneCount = 0;

        for (SubTask subTask : subTasks) {
            switch (subTask.status) {
                case NEW:
                    newCount++;
                    break;
                case DONE:
                    doneCount++;
                    break;
            }
        }

        if (subTasks.size() == newCount) {
            epic.status = TaskStatus.NEW;
        } else if (subTasks.size() == doneCount) {
            epic.status = TaskStatus.DONE;
        } else {
            epic.status = TaskStatus.IN_PROGRESS;
        }
    }


}
