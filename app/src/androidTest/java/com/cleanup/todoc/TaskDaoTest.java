package com.cleanup.todoc;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;

import com.cleanup.todoc.database.ToDocDatabase;
import com.cleanup.todoc.models.Project;
import com.cleanup.todoc.models.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class TaskDaoTest {

    // FOR DATA
    private ToDocDatabase database;

    // DATA SET FOR TEST
    private static long PROJECT_ID = 4L;
    private static Project PROJECT_DEMO = new Project(PROJECT_ID, "Clean", 0xFFD67EC3);
    private static long TASK_ID = 1L;
    private static Task TASK_DEMO = new Task(TASK_ID,PROJECT_ID,"Passer l'aspirateur",System.currentTimeMillis());

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                ToDocDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }

    @Test
    public void insertAndGetUser() throws InterruptedException {
        // BEFORE : Adding a new project
        this.database.projectDao().createProject(PROJECT_DEMO);
        // TEST
        Project project = LiveDataTestUtil.getValue(this.database.projectDao().getProject(PROJECT_ID));
        assertTrue(project.getName().equals(PROJECT_DEMO.getName()) && project.getId() == PROJECT_ID);
    }

    @Test
    public void getTasksWhenNoTaskInserted() throws InterruptedException {
        //TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks(PROJECT_ID));
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void insertAndGetTasks() throws InterruptedException{
        //BEFORE : Adding demo project and demo task
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().insertTask(TASK_DEMO);

        //TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks(PROJECT_ID));
        assertTrue(tasks.size() == 1);
    }

    @Test
    public void insertAndUpdateItem() throws InterruptedException {
        // BEFORE : Adding demo project & demo task. Next, update task added & re-save it
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().insertTask(TASK_DEMO);
        Task taskAdded = LiveDataTestUtil.getValue(this.database.taskDao().getTasks(PROJECT_ID)).get(0);
        taskAdded.setName("Repassage");
        this.database.taskDao().updateTask(taskAdded);

        //TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks(PROJECT_ID));
        assertTrue(tasks.size() == 1);
        assertEquals("Repassage", tasks.get(0).getName());
    }

    @Test
    public void insertAndDeleteItem() throws InterruptedException {
        // BEFORE : Adding demo user & demo item. Next, get the item added & delete it.
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().insertTask(TASK_DEMO);
        Task taskAdded = LiveDataTestUtil.getValue(this.database.taskDao().getTasks(PROJECT_ID)).get(0);
        this.database.taskDao().deleteTask(taskAdded.getId());

        //TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks(PROJECT_ID));
        assertTrue(tasks.isEmpty());
    }
}
