package com.example.michau.taskplanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class ListViewActivity extends AppCompatActivity {

    public static ArrayList<Task> taskArrayList = new ArrayList<>();;
    public static ListView listView;
    public static ListViewAdapter adapter;
    public static DatabaseHelper dbHelper;
    public static Context appContext;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        appContext = getApplicationContext();

        listView=(ListView)findViewById(R.id.taskListView);
        button = (Button) findViewById(R.id.menuButton);

        dbHelper = new DatabaseHelper(this);
        taskArrayList = dbHelper.getTaskList();
        adapter = new ListViewAdapter(taskArrayList,getApplicationContext());

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Task dataModel = taskArrayList.get(position);

                Intent intent = new Intent(ListViewActivity.this, TaskActivity.class);
                intent.putExtra("task", dataModel);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Task dataModel = taskArrayList.get(position);

                Intent intent = new Intent(ListViewActivity.this, EditTaskActivity.class);
                intent.putExtra("task", dataModel);
                startActivity(intent);

                return true;
            }
        });
    }

    public static void addTaskToList(Task task){
        dbHelper.insertData(task);
        adapter = new ListViewAdapter(taskArrayList, appContext);

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public static void editTask(Task taskBeforeEditing, Task taskAfterEditing){
        dbHelper.updateData(taskBeforeEditing, taskAfterEditing);
        adapter = new ListViewAdapter(taskArrayList,appContext);

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.notifyDataSetChanged();
    }

    public void refreshListView(){
        adapter = new ListViewAdapter(taskArrayList,getApplicationContext());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void menu(View view) {
        PopupMenu popup = new PopupMenu(this,view);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu,popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.sort_name_item:
                        taskArrayList = dbHelper.sortByName();
                        refreshListView();
                        return true;
                    case R.id.sort_date_item:
                        taskArrayList = dbHelper.sortByDate();
                        refreshListView();
                        return true;
                    case R.id.sort_priority_item:
                        taskArrayList = dbHelper.sortByPriority();
                        refreshListView();
                        return true;
                    case R.id.delete_item:
                        dbHelper.deleteFinishedTasks();
                        taskArrayList = dbHelper.getTaskList();
                        refreshListView();
                        return true;
                    case R.id.home_item:
                        startActivity(new Intent(ListViewActivity.this, MainActivity.class));
                        return true;
                    default:
                        return false;
                }
            }
        });

        popup.show();
    }
}
