package com.example.michau.taskplanner;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "task_table";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE task_table ( " +
                " ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " Name VARCHAR2(30), " +
                " TaskDescription VARCHAR2(20)," +
                " Date VARCHAR2(10)," +
                " Status VARCHAR2(30)," +
                " Priority VARCHAR2(30))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public void insertData(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("INSERT INTO task_table (Name, TaskDescription, Date, Status, Priority) " +
                "VALUES (?,?,?,?,?)");
        stmt.bindString(1, task.getName());
        stmt.bindString(2, task.getTaskDescription());
        stmt.bindString(3, task.getDate());
        stmt.bindString(4, task.getStatus());
        stmt.bindString(5, task.getPriority());
        stmt.execute();
        stmt.close();
        db.close();
    }

    public ArrayList getTaskList() {
        ArrayList tasksList = new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT Name, TaskDescription, Date, Status, Priority from task_table";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            Task task = new Task();
            task.setName(cursor.getString(0));
            task.setTaskDescription(cursor.getString(1));
            task.setDate(cursor.getString(2));
            task.setStatus(cursor.getString(3));
            task.setPriority(cursor.getString(4));
            tasksList.add(task);
        }
        db.close();
        return tasksList;
    }

    public void updateData(Task taskBeforeEditing, Task taskAfterEediting){
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("UPDATE task_table SET Name=?, TaskDescription=?, Date=?, Status=?, Priority=? "+
                "WHERE Name=? AND TaskDescription=? AND Date=? AND Status=? AND Priority=?");
        stmt.bindString(1, taskAfterEediting.getName());
        stmt.bindString(2, taskAfterEediting.getTaskDescription());
        stmt.bindString(3, taskAfterEediting.getDate());
        stmt.bindString(4, taskAfterEediting.getStatus());
        stmt.bindString(5, taskAfterEediting.getPriority());

        stmt.bindString(6, taskBeforeEditing.getName());
        stmt.bindString(7, taskBeforeEditing.getTaskDescription());
        stmt.bindString(8, taskBeforeEditing.getDate());
        stmt.bindString(9, taskBeforeEditing.getStatus());
        stmt.bindString(10, taskBeforeEditing.getPriority());

        stmt.execute();
        stmt.close();
        db.close();
    }

    public ArrayList sortByName() {
        ArrayList tasksList = new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT Name, TaskDescription, Date, Status, Priority from task_table order by Name asc";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            Task task = new Task();
            task.setName(cursor.getString(0));
            task.setTaskDescription(cursor.getString(1));
            task.setDate(cursor.getString(2));
            task.setStatus(cursor.getString(3));
            task.setPriority(cursor.getString(4));
            tasksList.add(task);
        }
        db.close();
        //ListViewActivity.adapter.notifyDataSetChanged();
        return tasksList;
    }

    public ArrayList sortByDate() {
        ArrayList tasksList = new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT Name, TaskDescription, Date, Status, Priority from task_table order by Date asc";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            Task task = new Task();
            task.setName(cursor.getString(0));
            task.setTaskDescription(cursor.getString(1));
            task.setDate(cursor.getString(2));
            task.setStatus(cursor.getString(3));
            task.setPriority(cursor.getString(4));
            tasksList.add(task);
        }
        db.close();
        //ListViewActivity.adapter.notifyDataSetChanged();
        return tasksList;
    }

    public ArrayList sortByPriority() {
        ArrayList tasksList = new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT Name, TaskDescription, Date, Status, Priority from task_table " +
                "WHERE Priority = 'Wysoki'";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            Task task = new Task();
            task.setName(cursor.getString(0));
            task.setTaskDescription(cursor.getString(1));
            task.setDate(cursor.getString(2));
            task.setStatus(cursor.getString(3));
            task.setPriority(cursor.getString(4));
            tasksList.add(task);
        }
        db.close();
        //ListViewActivity.adapter.notifyDataSetChanged();
        return tasksList;
    }

    public void deleteFinishedTasks(){
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("DELETE FROM task_table WHERE Status = 'Zakończone' ");
        stmt.execute();
        stmt.close();
        db.close();
    }
}
