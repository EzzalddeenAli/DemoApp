package com.tahmazyan.core.event;

public class Event<T> {

    private T data;
    private int taskId;

    public Event(T data, int taskId) {
        this.data = data;
        this.taskId = taskId;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
