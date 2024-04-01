import Entities.Epic;
import Entities.SubTask;
import Entities.Task;
import Managers.HistoryManager;
import Managers.Managers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static Entities.TaskStatus.NEW;

public class InMemoryHistoryManagerTest {
    @Test
    void add() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        Task task = new Task("Test", "Test description", NEW, 1);
        Epic epic = new Epic("Test", "Test description", 2);
        SubTask subTask = new SubTask("Test", "Test description", NEW, 3);
        historyManager.add(task);
        historyManager.add(epic);
        historyManager.add(subTask);
        final List<Task> history = historyManager.getHistory();
        Assertions.assertNotNull(history, "История не пустая.");
        Assertions.assertEquals(3, history.size(), "История не пустая.");
    }
}
