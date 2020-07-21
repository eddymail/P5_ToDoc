package com.cleanup.todoc.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.cleanup.todoc.models.Project;
import com.cleanup.todoc.models.Task;
import com.cleanup.todoc.repository.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    // REPOSITORIES
    private final TaskDataRepository taskDataSource;
    private final Executor executor;

    // DATA
    private MutableLiveData<Project> currentProject = new MutableLiveData<>();

    public TaskViewModel(TaskDataRepository taskDataSource, Executor executor) {
        this.taskDataSource = taskDataSource;
        this.executor = executor;
    }

    public void init(long projectId) {
        if (this.currentProject != null) {
            return;
        }
        currentProject.setValue(Project.getProjectById(projectId));
    }

    // -----------------
    // FOR PROJECT
    // -----------------

    public LiveData<Project> getProject(long projectId) { return this.currentProject; }

    // -----------------
    // FOR TASK
    // -----------------

    public LiveData<List<Task>> getTasks(long projectId) { return taskDataSource.getTasks(projectId); }

    public void createTask(Task task) {
        executor.execute(() -> {
            taskDataSource.createTask(task);
        });
    }

    public void deleteTask(long projectId) {
        executor.execute(() -> {
            taskDataSource.deleteTask(projectId);
        });
    }

    public void updateTask(Task task) {
        executor.execute(() -> {
            taskDataSource.updateTask(task);
        });
    }
}
