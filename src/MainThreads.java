import functions.Functions;
import functions.basic.Exp;
import functions.basic.Log;
import functions.threads.*;

public class MainThreads {
    public static void main(String[] args) {
        System.out.println("Потоки и интегрирование\n");

        System.out.println("Метод интегрирования");
        testIntegration();

        System.out.println("\n\nПоследовательная версия");
        nonThread();

        System.out.println("\n\nПростая многопоточная версия");
        simpleThreads();

        System.out.println("\n\nУсовершенствованная многопоточная версия");
        complicatedThreads();
    }

    private static void testIntegration() {
        System.out.println("Тестирование интегрирования экспоненты на отрезке [0, 1]");

        Exp exp = new Exp();
        double left = 0;
        double right = 1;
        double theoretical = Math.E - 1;

        System.out.printf("Теоретическое значение: %.10f\n", theoretical);

        System.out.println("\nПоиск шага для точности 10^-7:");
        double step = 0.1;
        double diff;
        int iteration = 0;

        do {
            double result = Functions.integrate(exp, left, right, step);
            diff = Math.abs(result - theoretical);
            System.out.printf("Итерация %d: шаг = %.10f, результат = %.10f, разница = %.10f\n",
                    iteration, step, result, diff);
            step /= 2;
            iteration++;
        } while (diff > 1e-7 && step > 1e-12);

        System.out.printf("\nДостигнута точность 10^-7 при шаге = %.10f\n", step * 2);
    }

    public static void nonThread() {
        System.out.println("Последовательная версия программы:");

        Task task = new Task(100);

        for (int i = 0; i < task.getTasksCount(); i++) {
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

            try {
                double result = Functions.integrate(task.getFunction(), task.getLeft(), task.getRight(), task.getStep());

                System.out.printf("Result %.2f %.2f %.2f %.2f%n", left, right, step, result);
            } catch (IllegalArgumentException e) {
                System.out.printf("Error for %.2f %.2f %.2f: %s%n", left, right, step, e.getMessage());
            }
        }
    }

    public static void simpleThreads() {
        System.out.println("Запуск простой многопоточной версии:");
        Task task = new Task(100);

        Thread generatorThread = new Thread(new SimpleGenerator(task));
        Thread integratorThread = new Thread(new SimpleIntegrator(task));

        System.out.println("Установка приоритетов: генератор - MIN, интегратор - MAX");
        generatorThread.setPriority(Thread.MIN_PRIORITY);
        integratorThread.setPriority(Thread.MAX_PRIORITY);

        generatorThread.start();
        integratorThread.start();

        try {
            generatorThread.join(5000);
            integratorThread.join(1000);

            if (generatorThread.isAlive() || integratorThread.isAlive()) {
                System.out.println("\nТАЙМАУТ: Потоки зависли, принудительное прерывание...");
                generatorThread.interrupt();
                integratorThread.interrupt();

                Thread.sleep(500);
            }

            System.out.println("Потоки завершены: генератор - " +
                    (generatorThread.isAlive() ? "Не завершен" : "завершен") +
                    ", интегратор - " +
                    (integratorThread.isAlive() ? "Не завершен" : "завершен"));

        } catch (InterruptedException e) {
            System.out.println("Основной поток прерван");
        }

        System.out.println("Простая многопоточная версия завершена");
    }

    public static void complicatedThreads() {
        System.out.println("Запуск усовершенствованной многопоточной версии:");

        Task task = new Task(100);
        Semaphore semaphore = new Semaphore();

        Generator generator = new Generator(task, semaphore);
        Integrator integrator = new Integrator(task, semaphore);

        generator.setPriority(Thread.NORM_PRIORITY);
        integrator.setPriority(Thread.NORM_PRIORITY);

        generator.start();
        integrator.start();

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            System.out.println("Основной поток прерван");
        }

        System.out.println("\nПрерывание потоков после 50мс.............");

        generator.interrupt();
        integrator.interrupt();

        try {
            long timeout = 2000; // 2 секунды
            long startTime = System.currentTimeMillis();

            while ((generator.isAlive() || integrator.isAlive()) &&
                    (System.currentTimeMillis() - startTime) < timeout) {
                Thread.sleep(100);
            }

            if (generator.isAlive() || integrator.isAlive()) {
                System.out.println("Потоки не завершились за таймаут, принудительная остановка");
            }

        } catch (InterruptedException e) {
            System.out.println("Основной поток прерван при ожидании завершения");
        }

        System.out.println("Усовершенствованная многопоточная версия завершена");
    }
}