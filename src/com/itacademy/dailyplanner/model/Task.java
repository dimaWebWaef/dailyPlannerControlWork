package com.itacademy.dailyplanner.model;

public class Task {
    private  String nameTask;
    private  String taskDescription ;
    private  int executionDate;
    private  String priorityOption;
    private  boolean status;
    private  int id;

    public  Task (String nameTask, String taskDescription, int executionDate, String priorityOption, boolean status, int id){
                            //Валидация

        if ( id == 0){
            throw  new IllegalArgumentException ("ID не может быть 0");
        }

        if (nameTask == null){
            throw  new IllegalArgumentException("Имя задачи не может быть пустым");
        }
        if (nameTask.length() > 25){
            throw  new IllegalArgumentException("Имя задачи не может быть болeе 25 символов");
        }

        if (taskDescription == null){
            throw  new IllegalArgumentException("Описание задачи не может быть пустым");
        }
        if (taskDescription.length() > 50){
            throw  new IllegalArgumentException("Описание задачи не может быть болeе 50 символов");
        }

        this.nameTask = nameTask;
        this.taskDescription = taskDescription;
        this.executionDate =executionDate;
        this.priorityOption = priorityOption;
        this.status = status;
        this.id = id;
    }
     // Проверка на пустату
    private void validateNul(String valie, String faledName){
        if (valie == null);
        throw new IllegalArgumentException( faledName+ "не может быть пустым");
    }

    public String getNameTask() {
        return nameTask;
    }
    public  String getTaskDescription(){
        return  taskDescription;
    }
    public  int getExecutionDate(){
        return  executionDate;
    }
    public String getPriorityOption(){
        return  priorityOption;
    }
    public  boolean getStatus(){
        return  status;
    }
    public int getId(){
        return  id;
    }

    public void setNameTask(String nameTask) {
        validateNul(nameTask,"Имя задачи");
        if (nameTask.length() > 25){
            throw  new IllegalArgumentException("Имя задачи не может быть болeе 25 символов");
        }
        this.nameTask = nameTask;
    }

    public  void  setTaskDescription(String taskDescription){
        validateNul(taskDescription,"Описание задачи");
        if ( taskDescription.length() >50){
            throw new  IllegalArgumentException("Описание задачи не может быть болeе 50 символов");
        }
        this.taskDescription = taskDescription;
    }
    public  void  setExecutionDate( int executionDate){
        this.executionDate =executionDate;
    }
    public  void  setPriorityOption (String priorityOption){
        this.priorityOption = priorityOption;
    }
    public void  setStatus  (boolean status){
        this.status = status;
    }
    public  void  setId (int id){
        this.id = id;
    }
}

