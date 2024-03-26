package com.pablo.todoapp.addtask.ui.Model

import java.sql.Time

data class TaskModel(val id:Long = System.currentTimeMillis(),val task:String,var selected:Boolean = false) {
}