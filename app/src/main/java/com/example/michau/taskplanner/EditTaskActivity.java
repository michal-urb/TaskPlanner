package com.example.michau.taskplanner;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class EditTaskActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText taskName, taskDescription, taskDate;
    Spinner taskStatus, taskPriority;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        taskName = (EditText)findViewById(R.id.nameEditText);
        taskDescription = (EditText)findViewById(R.id.descriptionEditText);
        taskDate = (EditText)findViewById(R.id.dateEditText);
        button = (Button) findViewById(R.id.editButton);

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "dd-MM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                taskDate.setText(sdf.format(myCalendar.getTime()));
            }
        };

        taskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditTaskActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        taskStatus = (Spinner) findViewById(R.id.statusSpinner);
        taskPriority = (Spinner) findViewById(R.id.prioritySpinner);

        ArrayList<String> statusList = new ArrayList<String>();
        statusList.add("Do zrobienia");
        statusList.add("Zakończone");

        ArrayList<String> priorityList = new ArrayList<String>();
        priorityList.add("Niski");
        priorityList.add("Średni");
        priorityList.add("Wysoki");

        taskStatus.setOnItemSelectedListener(this);
        taskPriority.setOnItemSelectedListener(this);

        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statusList);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskStatus.setAdapter(statusAdapter);

        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, priorityList);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskPriority.setAdapter(priorityAdapter);

        Intent intent = getIntent();
        final Task task = (Task)intent.getSerializableExtra("task");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editButtonClick(task);
            }
        });

        setProperties(task, statusAdapter, priorityAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void editButtonClick(Task task){
        if(!anyTextBoxIsEmpty()) {
            String status = taskStatus.getSelectedItem().toString();
            String priority = taskPriority.getSelectedItem().toString();

            Task newTask = new Task(
                    taskName.getText().toString(),
                    taskDescription.getText().toString(),
                    taskDate.getText().toString(),
                    status,
                    priority
            );

            ListViewActivity.editTask(task, newTask);

            Intent intent = new Intent(EditTaskActivity.this, ListViewActivity.class);
            startActivity(intent);
        }
    }

    private boolean anyTextBoxIsEmpty(){
        if(taskName.getText().toString().isEmpty() || taskName.getText().toString().equals(null))
            return true;
        else if(taskDescription.getText().toString().isEmpty() || taskDescription.getText().toString().equals(null))
            return true;
        else if(taskDate.getText().toString().isEmpty() || taskDate.getText().toString().equals(null))
            return true;
        else
            return false;
    }

    private void setProperties(Task task, ArrayAdapter<String> statusAdapter, ArrayAdapter<String> priorityAdapter){
        int selectionPosition = statusAdapter.getPosition(task.getStatus());
        taskStatus.setSelection(selectionPosition);

        int position = priorityAdapter.getPosition(task.getPriority());
        taskPriority.setSelection(position);

        taskName.setText(task.getName());
        taskDescription.setText(task.getTaskDescription());
        taskDate.setText(task.getDate());
    }
}
