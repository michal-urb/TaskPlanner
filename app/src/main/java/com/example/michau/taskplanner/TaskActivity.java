package com.example.michau.taskplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TaskActivity extends AppCompatActivity {

    TextView taskName, taskDescription, taskDate, taskStatus, taskPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        taskName = (TextView)findViewById(R.id.name);
        taskDescription = (TextView)findViewById(R.id.description);
        taskDate = (TextView)findViewById(R.id.date);
        taskStatus = (TextView)findViewById(R.id.status);
        taskPriority = (TextView)findViewById(R.id.priority);

        Intent intent = getIntent();
        Task task = (Task)intent.getSerializableExtra("task");

        taskName.setText(task.getName());
        taskDescription.setText(task.getTaskDescription());
        taskDate.setText(task.getDate().toString());
        taskStatus.setText(task.getStatus());
        taskPriority.setText(task.getPriority());
    }
}
