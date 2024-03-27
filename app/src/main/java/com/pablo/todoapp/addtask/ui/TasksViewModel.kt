package com.pablo.todoapp.addtask.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pablo.todoapp.addtask.domain.AddTaskUseCase
import com.pablo.todoapp.addtask.domain.DeleteTasksUseCase
import com.pablo.todoapp.addtask.domain.GetTasksUseCase
import com.pablo.todoapp.addtask.domain.UpdateTaskUseCase
import com.pablo.todoapp.addtask.ui.Model.TaskModel
import com.pablo.todoapp.addtask.ui.TasksUiState
import com.pablo.todoapp.addtask.ui.TasksUiState.Success
import com.pablo.todoapp.addtask.ui.TasksUiState.Loading
import com.pablo.todoapp.addtask.ui.TasksUiState.Error
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTasksUseCase: DeleteTasksUseCase,
    getTasksUseCase: GetTasksUseCase
) : ViewModel() {

    val uiState: StateFlow<TasksUiState> = getTasksUseCase().map(::Success)
        .catch { Error(it) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            Loading
        )

//    private val _tasks = mutableStateListOf<TaskModel>()
//    val tasks: List<TaskModel> = _tasks

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog


    fun onDialogClose() {
        _showDialog.value = false
    }

    fun onShowDialog() {
        _showDialog.value = true
    }

    fun onTasksCreated(task: String) {
        _showDialog.value = false

        viewModelScope.launch {
            addTaskUseCase(TaskModel(task = task))
        }

    }
/**Check**/
    fun onCheckBoxSelected(taskModel: TaskModel) {

        viewModelScope.launch {
            updateTaskUseCase(taskModel.copy(selected = !taskModel.selected))
        }
    }

    /**Remove**/
    fun onItemRemove(taskModel: TaskModel) {
        viewModelScope.launch {
            deleteTasksUseCase(taskModel)
        }


    }


}