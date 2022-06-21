package com.example.apka2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ElementDao {
    @Insert
    void insert(Element element);
    @Update
    void update(Element element);
    @Delete
    void delete(Element element);

    @Query("Delete FROM table1")
    void deleteAll();

    @Query("Select * FROM table1 Order by producent ASC")
    LiveData<List<Element>> getAlphabetizedElements();
}
