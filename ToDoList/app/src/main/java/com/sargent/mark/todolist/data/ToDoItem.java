package com.sargent.mark.todolist.data;

/**
 * Created by mark on 7/4/17.
 */

public class ToDoItem {
    private String description;
    private String dueDate;
    //adding status checker
    private boolean status;

    public ToDoItem(String description, String dueDate) {
        this.description = description;
        this.dueDate = dueDate;
        //setting the status to be false, or the task as not done
       // this.status = false;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

//    public void setStatus(boolean status){
//        this.status = status;
//    }
}
