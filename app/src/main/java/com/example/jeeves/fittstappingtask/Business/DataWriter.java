package com.example.jeeves.fittstappingtask.Business;

import android.content.Context;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class DataWriter {

    private Context context;
    private String filename;

    public DataWriter(Context context, String filename) {
        this.context = context;
        this.filename = filename;
    }

    public void appendResultsToFile(String data){
//        File file = new File(context.getFilesDir(), filename);
//        if(!file.exists()){
//            file.mkdir();
//        }
        try
        {
            File experimentResults = new File(context.getFilesDir(), filename);
            FileWriter writer = new FileWriter(experimentResults, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);

            if (!experimentResults.exists()) {
                experimentResults.createNewFile();
            }

            printWriter.print(data);
            printWriter.close();

        }
        catch (Exception e){
            e.printStackTrace();

        }
    }
}

