package com.yourdomain.taskmanagementapp.composables.main

// Tip: Use Ctrl+Alt+O (Windows/Linux) or Option+Cmd+O (Mac) to organize imports
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourdomain.taskmanagementapp.ui.theme.TaskManagementTheme

@Composable
fun TaskScreen(modifier: Modifier = Modifier) {

    // 1. STATE: Use 'rememberSaveable' with a 'listSaver' to make the list
    // survive configuration changes (like screen rotation) and process death.
    val tasks = rememberSaveable(saver = listSaver(
        save = { stateList -> stateList.toList() }, // How to save the list
        restore = { list -> mutableStateListOf(*list.toTypedArray()) } // How to restore it
    )) {
        mutableStateListOf<String>() // Initial value is an empty mutable list
    }
    var taskName by rememberSaveable { mutableStateOf("") } // Keep this as rememberSaveable

    val onAddTask: () -> Unit = {
        val trimmedTaskName = taskName.trim() // Trim whitespace
        if (trimmedTaskName.isNotBlank()) {
            // Add the trimmed task name to our mutable list.
            // Compose observes this list and triggers recomposition.
            tasks.add(trimmedTaskName)
            taskName = "" // Clear the input field
        } else {
            // TODO: Implement user-facing error feedback (e.g., using SnackbarHostState)
            println("Task name is blank or only whitespace, not adding.") // Check Logcat for this
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally // Center empty text
    ) {

        // This Row holds the input field and the button
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // 2. UI: The TextField for input
            OutlinedTextField(
                value = taskName,
                onValueChange = { newText -> taskName = newText },
                label = { Text("Enter a new task") },
                modifier = Modifier
                    .weight(1f) // Makes the field fill available space
                    .semantics { contentDescription = "Enter new task field" },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { onAddTask() })
            )

            // 3. LOGIC: The Button to add the task
            Button(
                onClick = onAddTask,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .semantics { contentDescription = "Add task button" }
            ) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        // 2. UI: Display placeholder or the list
        if (tasks.isEmpty()) {
            Text(
                text = "No tasks yet! Add one above.",
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Use 'items' with a key for performance and stability.
                // Here, we use the task string itself as the key.
                // For real apps, use a unique ID if available.
                items(
                    items = tasks,
                    key = { task -> task } // Provide a stable key for each item
                ) { task ->
                    TaskItem(taskName = task)
                }
            }
        }
    }
}

//Add two previews to see how our component looks in both light and dark themes.

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun TaskScreenPreview() {
    TaskManagementTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            TaskScreen()
        }
    }
}

@Preview(
    showBackground = true,
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun TaskScreenDarkPreview() {
    TaskManagementTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            TaskScreen()
        }
    }
}