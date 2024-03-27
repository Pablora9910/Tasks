package com.pablo.todoapp.addtask.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
@Dao
interface TasksDao {
    @Query("SELECT * FROM TaskEntity")
    fun getTasks(): Flow<List<TaskEntity>>

    @Insert
    suspend fun addTask(item:TaskEntity)

    @Update()
    suspend fun updatedTasks(item:TaskEntity)
    @Delete
    suspend fun deleteTasks(item:TaskEntity)

}