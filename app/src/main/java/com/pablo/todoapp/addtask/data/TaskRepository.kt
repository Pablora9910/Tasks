package com.pablo.todoapp.addtask.data

import com.pablo.todoapp.addtask.ui.Model.TaskModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(private val tasksDao: TasksDao) {

    val tasks: Flow<List<TaskModel>> =
        tasksDao.getTasks().map { items -> items.map { TaskModel(it.id, it.task, it.seleted) } }

    suspend fun add(taskModel: TaskModel) {
        tasksDao.addTask(taskModel.toData())
    }

    suspend fun update(taskModel: TaskModel){
        tasksDao.updatedTasks(taskModel.toData())
    }

    suspend fun delete(taskModel: TaskModel){
        tasksDao.deleteTasks(taskModel.toData())
    }


}
fun TaskModel.toData():TaskEntity{
    return TaskEntity(this.id, this.task, this.selected)
}