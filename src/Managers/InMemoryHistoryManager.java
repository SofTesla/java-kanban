package Managers;

import Entities.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    ArrayList<Task> history;

    public InMemoryHistoryManager() {
        history = new ArrayList<>();
    }


    @Override
    public ArrayList<Task> getHistory() {
        return history;
    }

    public void add(Task task) {
        if (task == null) {
            return;
        }
        if (history.size() >= 10) {
            history.removeFirst();
        }

            history.add(task);

        }
    }

