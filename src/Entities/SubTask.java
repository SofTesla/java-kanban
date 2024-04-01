package Entities;

public class SubTask extends Task {
    protected Epic epic;

    public Epic getEpic() {
        return epic;
    }

    public SubTask(String name, String description, TaskStatus status, int id) {
        super(name, description, status, id);
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }

    @Override
    public String toString() {
        return "Entities.SubTask{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status=" + getStatus() +
                '}';
    }
}
