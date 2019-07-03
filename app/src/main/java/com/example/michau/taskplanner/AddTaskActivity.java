package com.example.michau.taskplanner;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText taskName, taskDescription, taskDate;
    Spinner taskStatus, taskPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskName = (EditText)findViewById(R.id.nameEditText);
        taskDescription = (EditText)findViewById(R.id.descriptionEditText);
        taskDate = (EditText)findViewById(R.id.dateEditText);

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
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
                new DatePickerDialog(AddTaskActivity.this, date, myCalendar
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
    }

    public void addButtonOnClick(View view) {
        if(!anyTextBoxIsEmpty()) {

            Task task = new Task(
                    taskName.getText().toString(),
                    taskDescription.getText().toString(),
                    taskDate.getText().toString(),
                    taskStatus.getSelectedItem().toString(),
                    taskPriority.getSelectedItem().toString()
            );

            ListViewActivity.addTaskToList(task);

            Intent intent = new Intent(AddTaskActivity.this, ListViewActivity.class);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
