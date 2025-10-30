package com.yourdomain.taskmanagementapp.composables.main

// Tip: Use Ctrl+Alt+O (Windows/Linux) or Option+Cmd+O (Mac) to organize imports
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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

    // 1. STATE: Use 'rememberSaveable' to make our state (the text)
    // survive configuration changes like screen rotation.
    var taskName by rememberSaveable { mutableStateOf("") }

    val onAddTask: () -> Unit = {
        if (taskName.isNotBlank()) {
            // This outputs to the "Logcat" panel in Android Studio.
            // In the next step, we'll add this to a real list.
            println("Task to add: $taskName")
            taskName = "" // Clear the field after adding
        } else {
            // TODO: Show a user-facing error (e.g., a Snackbar)
            println("Task name is blank, not adding.")
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top // We'll add a list below later
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