package com.cleanup.todoc.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.cleanup.todoc.models.Project;
import com.cleanup.todoc.models.ProjectDataBase;

@Dao
public interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createProject(ProjectDataBase projectDataBase);

    @Query("SELECT * FROM ProjectDataBase WHERE id = :projectDataBaseId")
    LiveData<Project> getProject(long projectDataBaseId);
}
