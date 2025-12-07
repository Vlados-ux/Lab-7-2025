package functions.threads;

public class Semaphore {
    private boolean canWrite = true;
    private boolean canRead = false;

    public synchronized void startWrite() throws InterruptedException {
        while (!canWrite) {
            wait();
        }
        canWrite = false;
    }

    public synchronized void endWrite() {
        canRead = true;
        notifyAll();
    }

    public synchronized void startRead() throws InterruptedException {
        while (!canRead) {
            wait();
        }
        canRead = false;
    }

    public synchronized void endRead() {
        canWrite = true;
        notifyAll();
    }
}
