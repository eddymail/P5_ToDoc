package com.cleanup.todoc.injection;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.cleanup.todoc.repository.TaskDataRepository;
import com.cleanup.todoc.ui.TaskViewModel;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final TaskDataRepository taskDataSource;

    private final Executor executor;

    public ViewModelFactory(TaskDataRepository taskDataSource, Executor executor) {
        this.taskDataSource = taskDataSource;
        this.executor = executor;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)) {
            return (T) new TaskViewModel(taskDataSource, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel Class");
    }
}
