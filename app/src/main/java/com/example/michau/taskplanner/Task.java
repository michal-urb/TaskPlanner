package com.example.michau.taskplanner;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {
    public String Name;
    public String TaskDescription;
    public String Date;
    public String Status;
    public String Priority;


    public Task(String name, String taskDescription, String date, String status, String priority) {
        Name = name;
        TaskDescription = taskDescription;
        Date = date;
        Status = status;
        Priority = priority;
    }

    public Task() {}

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTaskDescription() {
        return TaskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        TaskDescription = taskDescription;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        Priority = priority;
    }
}
