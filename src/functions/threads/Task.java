package functions.threads;

import functions.Function;
import functions.basic.Log;

public class Task {
    private Function function;
    private double left;
    private double right;
    private double step;
    private int tasksCount;
    private boolean taskReady = false;

    public Task(int tasksCount) {
        this.tasksCount = tasksCount;
    }

    public int getTasksCount() {
        return tasksCount;
    }

    public void setTasksCount(int tasksCount) {
        this.tasksCount = tasksCount;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public double getLeft() {
        return left;
    }

    public void setLeft(double left) {
        this.left = left;
    }

    public double getRight() {
        return right;
    }

    public void setRight(double right) {
        this.right = right;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }

    public boolean isTaskReady() {
        return taskReady;
    }

    public void setTaskReady(boolean ready) {
        this.taskReady = ready;
    }
}