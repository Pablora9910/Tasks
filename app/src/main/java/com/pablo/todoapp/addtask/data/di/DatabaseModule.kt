package com.pablo.todoapp.addtask.data.di

import android.content.Context
import androidx.compose.runtime.rememberUpdatedState
import androidx.room.Room
import com.pablo.todoapp.addtask.data.TasksDao
import com.pablo.todoapp.addtask.data.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideTaskDao(todoDatabase: TodoDatabase):TasksDao{
        return  todoDatabase.taskDao()
    }
    @Provides
    @Singleton
    fun provideTodoDatabase(@ApplicationContext appContext: Context):TodoDatabase{
        return Room.databaseBuilder(appContext, TodoDatabase::class.java,"TaskDatabase").build()
    }
}