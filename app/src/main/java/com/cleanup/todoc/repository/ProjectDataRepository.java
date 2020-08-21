package com.cleanup.todoc.repository;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.models.Project;

public class ProjectDataRepository {

    private final ProjectDao projectDao;
    //Constructor
    public ProjectDataRepository(ProjectDao projectDao) { this.projectDao = projectDao; }
    //get project
    public LiveData<Project> getProject (long projectId) { return  this.projectDao.getProject(projectId); }
    //get all
    public LiveData<Project[]> getAll () { return  this.projectDao.getAll(); }
}
