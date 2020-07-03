package com.cleanup.todoc.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.cleanup.todoc.models.Project;

@Dao
public interface ProjectDao {

    // Permet d'écraser un utilisateur déjà existant possèdant le même id
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createProject(Project project);

    @Query("SELECT * FROM Project WHERE id =:projectId")
    LiveData<Project> getProject(long projectId);
}
