import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task {

    HashMap<Integer, SubTask> subTasks;

    public Epic(String name, String description) {
        super(name, description, TaskStatus.NEW);
        this.subTasks = new HashMap<>();
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", subTasks=" + new ArrayList<>(subTasks.values()) +
                '}';
    }
}
