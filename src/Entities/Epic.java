package Entities;

import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task {

    protected HashMap<Integer, SubTask> subTasks;

    public HashMap<Integer, SubTask> getSubTasks() {
        return subTasks;
    }

    public Epic(String name, String description) {
        super(name, description, TaskStatus.NEW);
        this.subTasks = new HashMap<>();
    }

    public void setSubTasks(HashMap<Integer, SubTask> subTasks) {
        this.subTasks = subTasks;
    }

    @Override
    public String toString() {
        return "Entities.Epic{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status=" + getStatus() +
                ", subTasks=" + new ArrayList<>(subTasks.values()) +
                '}';
    }
}
