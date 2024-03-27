package com.pablo.todoapp.addtask.domain

import com.pablo.todoapp.addtask.data.TaskRepository
import com.pablo.todoapp.addtask.ui.Model.TaskModel
import javax.inject.Inject

class DeleteTasksUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(taskModel: TaskModel){
        taskRepository.delete(taskModel)
    }
}