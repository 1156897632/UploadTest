package com.example.administrator.uploadtest;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/15.
 */

public class FileManage {

    public int number = 2;
    List<File> files = new ArrayList<>();

    public List<File> getFiles() {
        return files;
    }

    public void clearFiles() {
        files.clear();
    }

    public void divideFile(File file) throws Exception {
        if (file.exists()) {
            long uploadcontent = 0;
            long contentLength = file.length();
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            for (int i = 0; i < number; i++) {
                randomAccessFile.seek(uploadcontent);
                OutputStream outputStream = new FileOutputStream(files.get(i));
                int len;
                int total = 0;
                byte[] b = new byte[20];
                if (i != (number - 1)) {
                    while (((len = randomAccessFile.read(b)) != -1) && total <= (contentLength / number)) {
                        uploadcontent += len;
                        total += len;
                        outputStream.write(b, 0, len);
                        outputStream.flush();
                    }
                }
                if (i == number - 1) {
                    while ((len = randomAccessFile.read(b)) != -1) {
                        uploadcontent += len;
                        outputStream.write(b, 0, len);
                        outputStream.flush();
                    }
                }
                outputStream.close();
            }
        }
    }

    public void mergeFiles(String file_path,String file_name){
        if (files.size()>0){
            for (File file:files) {
                
            }
        }
    }

    public void createnewFile(String path) throws Exception {
        for (int i = 0; i < number; i++) {
            String file_name = "hhahaha_" + i + "txt";
            String file_path = path + "/" + file_name;
            File file = new File(file_path);
            files.add(file);
        }
    }

    public void deleteFile() {
        if (files.size() > 0) {
            for (File file : files) {
                if (file.exists()) {
                    file.delete();
                    Log.d("TAG", "delete " + file.getName());
                }
            }
        }
    }

}
