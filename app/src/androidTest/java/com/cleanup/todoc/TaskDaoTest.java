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

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class TaskDaoTest {

    // FOR DATA
    private ToDocDatabase database;

    // DATA SET FOR TEST
    private static long PROJECT_ID = 1;
    private static Project PROJECT_DEMO = new Project(PROJECT_ID, "Projet", 0xFFD67EC3);
    private static Task TASK_ASPIRATOR = new Task(1L,PROJECT_ID,"Passer l'aspirateur",new Date().getTime());
    private static Task TASK_WINDOW = new Task(2L,PROJECT_ID,"Laver les vitres",new Date().getTime());
    private static Task TASK_EMPTY = new Task(3L,PROJECT_ID,"Vider la corbeille",new Date().getTime());

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
    public void insertAndGetProject() throws InterruptedException {
        // BEFORE : Adding a new project
        this.database.projectDao().createProject(PROJECT_DEMO);
        // TEST
        Project project = LiveDataTestUtil.getValue(this.database.projectDao().getProject(PROJECT_ID));
        assertTrue(project.getName().equals(PROJECT_DEMO.getName()) && project.getId() == PROJECT_ID);
    }

    @Test
    public void getTasksWhenNoTaskInserted() throws InterruptedException {
        //TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void insertAndGetTasks() throws InterruptedException{
        //BEFORE : Adding demo project and demo task
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().insertTask(TASK_ASPIRATOR);
        this.database.taskDao().insertTask(TASK_EMPTY);
        this.database.taskDao().insertTask(TASK_WINDOW);
        //TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        assertEquals(3, tasks.size());
    }

    @Test
    public void insertAndDeleteTask() throws InterruptedException {
        // BEFORE : Adding demo user & demo item. Next, get the item added & delete it.
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().insertTask(TASK_ASPIRATOR);
        Task taskAdded = LiveDataTestUtil.getValue(this.database.taskDao().getTasks()).get(0);
        this.database.taskDao().deleteTask(taskAdded.getId());
        //TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        assertTrue(tasks.isEmpty());
    }
}
