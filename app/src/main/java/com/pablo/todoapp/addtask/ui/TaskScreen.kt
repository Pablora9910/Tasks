package com.pablo.todoapp.addtask.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.pablo.todoapp.addtask.ui.Model.TaskModel


@Composable
fun TaskScreen(tasksViewModel: TasksViewModel) {
    val livecycle = LocalLifecycleOwner.current.lifecycle
    val showDialog: Boolean by tasksViewModel.showDialog.observeAsState(false)

    val uiState by produceState<TasksUiState>(
        initialValue = TasksUiState.Loading,
        key1 = livecycle,
        key2 = tasksViewModel
    ) {
        livecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            tasksViewModel.uiState.collect { value = it }
        }
    }

    when (uiState) {
        is TasksUiState.Error -> {}
        TasksUiState.Loading -> {
            CircularProgressIndicator()
        }

        is TasksUiState.Success -> {
            Box(modifier = Modifier.fillMaxSize()) {
                AddTaskDialig(
                    showDialog,
                    onDismiss = { tasksViewModel.onDialogClose() },
                    onTasaskAdded = { tasksViewModel.onTasksCreated(it) },
                )
                FabDialog(Modifier.align(Alignment.BottomEnd), tasksViewModel)
            }
            TasksList((uiState as TasksUiState.Success).tasks,tasksViewModel)
        }
    }


}

@Composable
fun TasksList(tasks: List<TaskModel>,tasksViewModel: TasksViewModel) {
//    val myTasks: List<TaskModel> = tasksViewModel.tasks
    LazyColumn() {
        items(tasks, key = { it.id }) {
            ItemTask(taskModel = it, tasksViewModel = tasksViewModel)
        }
    }
}


@Composable
fun ItemTask(taskModel: TaskModel, tasksViewModel: TasksViewModel) {
    Card(
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        modifier = Modifier
            .padding(horizontal = 18.dp, vertical = 6.dp)
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = {
                    tasksViewModel.onItemRemove(taskModel)
                })
            }
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = taskModel.task, modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .padding(start = 10.dp), fontSize = 20.sp
            )
            Checkbox(
                checked = taskModel.selected,
                onCheckedChange = { tasksViewModel.onCheckBoxSelected(taskModel) })
        }
    }

}

@Composable
fun FabDialog(align: Modifier, tasksViewModel: TasksViewModel) {
    FloatingActionButton(
        modifier = align.padding(16.dp),
        shape = CircleShape,
        onClick = {
            tasksViewModel.onShowDialog()
        }) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "")
    }
}


@Composable
fun AddTaskDialig(
    show: Boolean,
    onDismiss: () -> Unit,
    onTasaskAdded: (String) -> Unit,
) {
    var myTasks by remember {
        mutableStateOf("")
    }
    val isEnable = rememberSaveable {
        mutableStateOf(false)
    }
    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(18.dp)
            ) {
                Text(
                    text = "Añade tu tarea",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.size(16.dp))
                TextField(
                    value = myTasks,
                    onValueChange = {
                        myTasks = it
                        isEnable.value = myTasks != ""
                    },
                    singleLine = true,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.size(16.dp))

                Button(
                    onClick = {
                        onTasaskAdded(myTasks)
                        myTasks = ""
                    }, Modifier.fillMaxWidth(),
                    enabled = isEnable.value
                ) {
                    Text(text = "Añadir tarea")
                }

            }
        }
    }
}

