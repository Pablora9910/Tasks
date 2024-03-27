package com.pablo.todoapp.addtask.data

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class TaskEntity (
    @PrimaryKey
    val id:Long,
    var task:String,
    var seleted: Boolean = false
)