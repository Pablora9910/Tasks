package com.pablo.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.pablo.todoapp.addtask.ui.TaskScreen
import com.pablo.todoapp.addtask.ui.TasksViewModel
import com.pablo.todoapp.ui.theme.TodoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val tasksViewModel: TasksViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoAppTheme {
                TaskScreen(tasksViewModel)
                //TaskScreen()

            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TodoAppTheme {

    }
}