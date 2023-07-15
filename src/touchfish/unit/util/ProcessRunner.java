package touchfish.unit.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessRunner {
    public Process process;
    public Thread readerThread;
    public ProcessReaderThreadRunner readerThreadRunner;
    public boolean enableOutputReader = true;
    public ProcessRunner() {}
    public ProcessRunner(String ... args) {
        start(args);
    }
    public Process start(String ... args) {
        System.out.println("# Process Starting...");
        try {
            process = Runtime.getRuntime().exec(args);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (enableOutputReader) {
            System.out.println("# Process Output Reader Starting...");
            readerThreadRunner = new ProcessReaderThreadRunner(process);
            readerThread = new Thread(readerThreadRunner);
            readerThread.start();
        }
        System.out.println("# Process Started");
        return process;
    }

    public void dispose() {
        System.out.println("# Process Destroy Forcibly...");
        process.destroyForcibly();
        if (readerThread != null) {
            System.out.println("# Reader Thread Interrupt...");
            readerThread.interrupt();
            readerThread = null;
            readerThreadRunner = null;
        }
        System.out.println("# Process Disposed");
    }

    public static void main(String[] args) throws InterruptedException {
        ProcessRunner process = new ProcessRunner();
        process.enableOutputReader = true;
        process.start("E:\\PyCharm\\PythonProjects\\dsau\\natural_language_processing\\dist\\web\\web.exe");
        Thread.sleep(5000);
        process.dispose();
    }
}
