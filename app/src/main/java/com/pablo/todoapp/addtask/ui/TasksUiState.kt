package com.pablo.todoapp.addtask.ui

import com.pablo.todoapp.addtask.ui.Model.TaskModel

sealed interface TasksUiState {
    object Loading:TasksUiState
    data class Error(val throwable: Throwable):TasksUiState
    data class Success(val tasks:List<TaskModel>):TasksUiState
}