package com.example.finditv2;

import android.content.Context;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
            List<Item> currentItems = getObject();
            if(currentItems == null){
                currentItems = new ArrayList<>();
            }

            currentItems.add(item);
            FileOutputStream fos = context.openFileOutput("data.bin", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(currentItems);
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the most recently added object from the data.bin file.
     * @return item
     */
    public static List<Item> getObject(){
        List<Item> item = null;
        try {
            FileInputStream fis = context.openFileInput("data.bin");
            ObjectInputStream is = new ObjectInputStream(fis);
            item = (List<Item>) is.readObject();
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    public static void removeObjects(){

        try{
            new FileOutputStream("data.bin").close();
        }
        catch (Exception e){
            e.printStackTrace();

        }
    }

    public static void removeItem(Item selectedItem) {
        try {
            List<Item> currentItems = getObject();
            if(currentItems == null){
                currentItems = new ArrayList<>();
            }

            currentItems.remove(selectedItem);
            FileOutputStream fos = context.openFileOutput("data.bin", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(currentItems);
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
