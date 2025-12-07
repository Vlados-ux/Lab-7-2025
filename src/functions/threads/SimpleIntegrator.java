package functions.threads;

import functions.Function;
import functions.Functions;

public class SimpleIntegrator implements Runnable {
    private Task task;

    public SimpleIntegrator(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Function func = null;
                double left = 0, right = 0, step = 0;
                boolean shouldProcess = false;
                synchronized (task) {
                    while (!task.isTaskReady() && task.getTasksCount() != -1) {
                        task.wait(100);
                    }

                    if (task.getTasksCount() == -1) {
                        break;
                    }

                    if (!task.isTaskReady()) {
                        continue;
                    }

                     func = task.getFunction();
                     left = task.getLeft();
                     right = task.getRight();
                     step = task.getStep();

                    task.setTaskReady(false);

                    task.notify();

                }

                try {
                    double result = Functions.integrate(func, left, right, step);
                    System.out.printf("Result %.2f %.2f %.2f %.2f%n",
                            left, right, step, result);
                } catch (IllegalArgumentException e) {
                    System.out.printf("SimpleInt: Error for %.2f %.2f %.2f: %s%n",
                            left, right, step, e.getMessage());
                }

                Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            System.out.println("SimpleIntegrator: прерван");
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.out.println("SimpleIntegrator ошибка: " + e.getMessage());
        }
    }
}