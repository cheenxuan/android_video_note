package org.base.hi.library.log;

import androidx.annotation.NonNull;

import org.base.hi.library.log.HiLogConfig;
import org.base.hi.library.log.HiLogMo;
import org.base.hi.library.log.HiLogPrinter;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Author: xuan
 * Created on 2023/2/28 15:32.
 * <p>
 * Describe:
 */
public class HiFilePrinter implements HiLogPrinter {

    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();
    private final String logPath;
    private final long retentionTime;
    private volatile PrintWorker worker;
    private LogWriter writer;
    private static HiFilePrinter instance;

    public static synchronized HiFilePrinter getInstance(String logPath, long retentionTime) {
        if (instance == null) {
            instance = new HiFilePrinter(logPath, retentionTime);
        }
        return instance;
    }

    public HiFilePrinter(String logPath, long retentionTime) {
        this.logPath = logPath;
        this.retentionTime = retentionTime;
        this.writer = new LogWriter();
        this.worker = new PrintWorker();
        cleanExpiredLog();

    }

    private void cleanExpiredLog() {
        if (retentionTime <= 0) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        File logDir = new File(logPath);
        File[] files = logDir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (currentTimeMillis - file.lastModified() > retentionTime) {
                file.delete();
            }
        }
    }

    @Override
    public void print(@NonNull @NotNull HiLogConfig config, int level, String tag, @NonNull @NotNull String printString) {
        long timeMillis = System.currentTimeMillis();
        if (!worker.isRunning()) {
            worker.start();
        }
        worker.put(new HiLogMo(timeMillis, level, tag, printString));
    }

    private class PrintWorker implements Runnable {
        private BlockingDeque<HiLogMo> logs = new LinkedBlockingDeque<>();
        private volatile boolean running;

        void put(HiLogMo log) {
            try {
                logs.put(log);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        boolean isRunning() {
            synchronized (this) {
                return running;
            }
        }

        void start() {
            synchronized (this) {
                EXECUTOR.execute(this);
                running = true;
            }
        }

        @Override
        public void run() {
            HiLogMo log;
            try {
                while (true) {
                    log = logs.take();
                    doPrint(log);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                synchronized (this) {
                    running = false;
                }
            }
        }
    }
    

    private void doPrint(HiLogMo log) {
        String lastFileName = writer.preFileName;
        if (lastFileName == null) {
            String newFileName = genFileName();
            if (writer.isReady()) {
                writer.close();
            }
            if (!writer.ready(newFileName)) {
                return;
            }
        }
        writer.append(log.flattenedLog());
    }

    private String genFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date(System.currentTimeMillis()));
    }


private class LogWriter {
    private String preFileName;
    private File logFile;
    private BufferedWriter bufferedWriter;

    boolean isReady() {
        return bufferedWriter != null;
    }

    String getPreFileName() {
        return preFileName;
    }

    boolean ready(String newFileName) {
        preFileName = newFileName;
        logFile = new File(logPath, newFileName);

        if (!logFile.exists()) {
            try {
                File parent = logFile.getParentFile();
                if (!parent.exists()) {
                    parent.mkdirs();
                }
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                preFileName = null;
                logFile = null;
                return false;
            }
        }

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(logFile, true));
        } catch (IOException e) {
            e.printStackTrace();
            preFileName = null;
            logFile = null;
            return false;
        }
        return true;
    }

    boolean close() {
        if (bufferedWriter != null) {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                bufferedWriter = null;
                preFileName = null;
                logFile = null;
            }
        }
        return true;
    }

    void append(String flattendLog) {
        try {
            bufferedWriter.write(flattendLog);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
}
