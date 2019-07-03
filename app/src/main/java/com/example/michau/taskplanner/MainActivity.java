package com.example.michau.taskplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void menuOnClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.llistViewButton:
                intent = new Intent(MainActivity.this, ListViewActivity.class);
                startActivity(intent);
                break;
            case R.id.addTaskButton:
                intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(intent);
                break;
            case R.id.infoButton:
                intent = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(intent);
                break;
        }
    }
}
