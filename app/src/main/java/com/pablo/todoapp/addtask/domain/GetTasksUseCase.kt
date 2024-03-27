package com.pablo.todoapp.addtask.domain

import androidx.room.Insert
import com.pablo.todoapp.addtask.data.TaskRepository
import com.pablo.todoapp.addtask.ui.Model.TaskModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(): Flow<List<TaskModel>> {
        return taskRepository.tasks
    }
}