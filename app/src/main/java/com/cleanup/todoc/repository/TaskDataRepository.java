package com.cleanup.todoc.repository;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.models.Task;

import java.util.List;

public class TaskDataRepository {

    private final TaskDao taskDao;
    //constructor
    public TaskDataRepository(TaskDao taskDao) {this.taskDao = taskDao; }
    //get
    public LiveData<List<Task>> getTasks(){ return this.taskDao.getTasks(); }
    //create
    public void createTask(Task task) { taskDao.insertTask(task); }
    //delete
    public void deleteTask(long taskId){ taskDao.deleteTask(taskId); }

}
