package com.lizhuquan.concurrency.phase2.chapter12_balking模式;

import java.io.FileWriter;
import java.io.IOException;

public class BalkingData {

    private String fileName;

    private String content;

    private boolean changed;

    public BalkingData(String fileName, String content) {
        this.fileName = fileName;
        this.content = content;
        this.changed = true;
    }

    public synchronized void change(String newContent) {
        this.content = newContent;
        this.changed = true;
    }

    public synchronized void save() throws IOException {
        if (!changed) {
            return;
        }

        doSave();
        this.changed = false;
    }

    private void doSave() throws IOException {
        System.out.println(">>>>> " + Thread.currentThread().getName() + " doSave " + content);
        try (FileWriter fileWriter = new FileWriter(fileName, true)) {
            fileWriter.write(content);
            fileWriter.write("\n");
            fileWriter.flush();
        }
    }
}
