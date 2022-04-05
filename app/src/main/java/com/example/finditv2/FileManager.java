package com.example.finditv2;

import android.content.Context;

import java.io.*;

public class FileManager {

    private static Context context;

    /**
     * FileManager constructor.
     * @param context = app context
     */
    public FileManager(Context context) {
        this.context = context;
    }

    /**
     * Makes an ObjectOutputStream and writes the item (object) to it.
     * @param item = Object being saved
     */
    public static void saveObject(Item item){
        try {
            FileOutputStream fos = context.openFileOutput("data.bin", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(item);
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the most recently added object from the data.bin file.
     * @return item
     */
    public static Item getObject(){
        Item item = null;
        try {
            FileInputStream fis = context.openFileInput("data.bin");
            ObjectInputStream is = new ObjectInputStream(fis);
            item = (Item) is.readObject();
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

}
