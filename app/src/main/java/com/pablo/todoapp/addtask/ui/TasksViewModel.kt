package com.pablo.todoapp.addtask.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pablo.todoapp.addtask.ui.Model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor():ViewModel()  {
    private val _tasks = mutableStateListOf<TaskModel>()
    val tasks:List<TaskModel> = _tasks

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog:LiveData<Boolean> = _showDialog



    fun onDialogClose() {
        _showDialog.value=false
    }
    fun onShowDialog() {
        _showDialog.value=true
    }
    fun onTasksCreated(task: String) {
        _showDialog.value=false
        _tasks.add(TaskModel(task=task))

    }

    fun onCheckBoxSelected(taskModel: TaskModel) {
        val index = _tasks.indexOf(taskModel)
        _tasks[index] = _tasks[index].let {
            it.copy(selected = !it.selected)
        }
    }


    fun onItemRemove(taskModel: TaskModel) {
        val task = _tasks.find { it.id == taskModel.id }
        _tasks.remove(task)

    }



}