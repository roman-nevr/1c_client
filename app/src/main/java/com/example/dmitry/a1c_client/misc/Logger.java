package com.example.dmitry.a1c_client.misc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 26.12.2016.
 */

public class Logger {
    private static final String PATH = "sdcard/cache/log.txt";
    private List<String> logs;
    private boolean saveOnExternalStorage;
    private BufferedWriter bw;

    public Logger(boolean saveOnExternalStorage) {
        this.saveOnExternalStorage = saveOnExternalStorage;
        logs = new ArrayList<>();
        if (saveOnExternalStorage) {
            File file = new File(PATH);
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }else {
                    file.delete();
                    file.createNewFile();
                }
                 bw = new BufferedWriter(new FileWriter(file, true));
            } catch (IOException e) {
                //fail
            }
        }
    }

    public void log(String message) {
        String log = "\n" + getCurrentTime() + ": " + message;
        logs.add(log);
        if (saveOnExternalStorage) {
            save(log);
        }
    }

    private void save(String log) {
        try {
            bw.write(log);
        } catch (IOException e) {
            e.printStackTrace();
            logs.add("write fails");
        }
    }

    private String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("mm:ss:SSS");
        return format.format(new Date());
    }

    public void clear() {
        logs.clear();
        logs.removeAll(logs);
    }

    public String print() {
        return logs.toString();
    }

    public void closeFile(){
        try {
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
