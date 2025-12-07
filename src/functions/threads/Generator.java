package functions.threads;

import functions.Function;
import functions.basic.Log;

public class Generator extends Thread {
    private Task task;
    private Semaphore semaphore;

    public Generator(Task task, Semaphore semaphore) {
        this.task = task;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < task.getTasksCount(); i++) {
                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException();
                }

                semaphore.startWrite();

                double base = 1 + Math.random() * 9;
                Log logFunc = new Log(base);

                double left = Math.random() * 100;
                double right = 100 + Math.random() * 100;
                double step = Math.random();

                task.setFunction(logFunc);
                task.setLeft(left);
                task.setRight(right);
                task.setStep(step);

                System.out.printf("Source %.2f %.2f %.2f%n", left, right, step);

                semaphore.endWrite();

                Thread.sleep(10);
            }

            semaphore.startWrite();
            task.setTasksCount(-1);
            semaphore.endWrite();

        } catch (InterruptedException e) {
            System.out.println("Генератор был прерван");
        }
    }
}
