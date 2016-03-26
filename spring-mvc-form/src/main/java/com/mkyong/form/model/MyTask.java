package com.mkyong.form.model;

public class MyTask {

    private String id;
    private String name;
    private String log;
    private String text;
    
    public MyTask() {
    }    
    
    public MyTask(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "MyTask [id=" + id + ", name=" + name + ", log=" + log + ", text=" + text + "]";
    }
    
}
