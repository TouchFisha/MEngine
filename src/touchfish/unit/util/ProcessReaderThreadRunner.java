package touchfish.unit.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * volatile:        访问顺序不稳定，保障效率高，并可以读写
 *                  可用于字段
 * synchronized:    效率低，保障访问顺序一定，并可以读写
 *                  可用于方法
 */
public class ProcessReaderThreadRunner implements Runnable {
    private Process process;
    private String output;
    public ProcessReaderThreadRunner(Process process) {
        this.process = process;
    }
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                BufferedReader reader = new BufferedReader(new BufferedReader(new InputStreamReader(process.getInputStream())));
                String line;
                while ((line = reader.readLine()) != null) {
                    addOutput(line);
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized String getOutput() {
        return output;
    }

    public synchronized void setOutput(String output) {
        this.output = output;
    }

    public synchronized void addOutput(String output) {
        this.output += output;
    }

    public synchronized void clearOutput() {
        this.output = "";
    }

}