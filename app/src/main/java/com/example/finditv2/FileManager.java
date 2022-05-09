package com.example.finditv2;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.*;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private static Context context; //TODO: Fix

    /**
     * FileManager constructor.
     * @param context = app context
     */
    public FileManager(Context context) {
        FileManager.context = context;
    }

    /**
     * Removes duplicate code for making a list with objects.
     * @return list.
     */
    private static List<?> makeItemList(Boolean isCat){
        List<?> currentItems = new ArrayList<>();
        if(isCat) currentItems = getCategories();
        else currentItems = getObject();

        return currentItems;
    }

    private static void makeFOS(String string, List<?> currentItems) throws IOException {
        FileOutputStream fos = context.openFileOutput(string, Context.MODE_PRIVATE);
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(currentItems);
        os.close();
    }

    private static List<?> makeFIS(String string) throws IOException, ClassNotFoundException {
        FileInputStream fis = context.openFileInput(string);
        ObjectInputStream is = new ObjectInputStream(fis);
        List<?> list = (List<?>) is.readObject();
        is.close();
        return list;
    }

    /**
     * Makes an ObjectOutputStream and writes the item (object) to it.
     * @param item = Object being saved
     */
    public static void saveObject(Item item){
        try {
            List<Item> currentItems = (List<Item>) makeItemList(false);
            currentItems.add(item);
            makeFOS("data.bin", currentItems);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves a new category into the local files(categories.bin)
     */
    public static void saveCategory(Category category){
        try {
            List<Category> currentItems = (List<Category>) makeItemList(true);
            currentItems.add(category);
            makeFOS("categories.bin", currentItems);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Experiment in having only one method for getting objects of any type. Works if an item is passed ( see attached code).
     * for (int i = 0; i < FileManager.getFromMem(item).size(); i++) {
     *         System.out.println(FileManager.getFromMem(item).get(i).getDescription());
     *     }
     */
    public static <T> List<T> getFromMem(T object){
        List<T> list = new ArrayList<>();
        String path = "";
        if(object.getClass().equals(Item.class)){
            path = "data.bin";
        }
        else path = "categories.bin";
        try {
            list = (List<T>) makeFIS(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Gets the most recently added object from the data.bin file.
     */
    public static List<Item> getObject(){
        List<Item> item = new ArrayList<>();
        try {
            item = (List<Item>) makeFIS("data.bin");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    /**
     * Pulls the list of categories which the app uses from categories.bin
     * @return the list of categories.
     */
    public static List<Category> getCategories(){
        List<Category> categories = new ArrayList<>();
        try {
            categories = (List<Category>) makeFIS("categories.bin");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }

    /**
     * Removes a specific item from the local files.
     * @param selectedItem = the item to be removed
     */
    public static void removeItem(Item selectedItem) {
        try {
            List<Item> currentItems = (List<Item>) makeItemList(false);
            currentItems.removeIf(fileItem -> fileItem.equals(selectedItem));
            makeFOS("data.bin", currentItems);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String saveToInternalStorage(Bitmap bitmapImage, String imageName){
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File filePath = new File(directory,imageName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 20, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath.getAbsolutePath();
    }

    public static Bitmap loadImageFromStorage(String path) {
        Bitmap bitmap = null;
        try {
            if (path != null) {
                File file = new File(path);
                bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}