package functions.threads;

import functions.Function;
import functions.Functions;

public class Integrator extends Thread {
    private Task task;
    private Semaphore semaphore;

    public Integrator(Task task, Semaphore semaphore) {
        this.task = task;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException();
                }

                semaphore.startRead();
                if (task.getTasksCount() == -1) {
                    semaphore.endRead();
                    break;
                }

                Function func = task.getFunction();
                double left = task.getLeft();
                double right = task.getRight();
                double step = task.getStep();

                semaphore.endRead();

                try {
                    double result = Functions.integrate(func, left, right, step);

                    System.out.printf("Result %.2f %.2f %.2f %.2f%n", left, right, step, result);
                } catch (IllegalArgumentException e) {
                    System.out.printf("Error for %.2f %.2f %.2f: %s%n", left, right, step, e.getMessage());
                }

                Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            System.out.println("Интегратор был прерван");
        }
    }
}
