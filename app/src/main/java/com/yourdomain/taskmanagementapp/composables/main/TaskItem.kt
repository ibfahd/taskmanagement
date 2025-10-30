package com.yourdomain.taskmanagementapp.composables.main

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourdomain.taskmanagementapp.ui.theme.TaskManagementTheme

@Composable
fun TaskItem(taskName: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // TODO: Add Checkbox later
        Text(
            text = taskName,
            modifier = Modifier
                .weight(1f)
                // Add semantics for screen readers
                .semantics { contentDescription = "Task: $taskName" },
            style = MaterialTheme.typography.bodyLarge
        )
        // TODO: Add Delete button later
    }
}

@Preview(showBackground = true)
@Composable
fun TaskItemPreview() {
    TaskManagementTheme { // Make sure this matches your theme name
        TaskItem(taskName = "Write the first draft")
    }
}