package com.example.finditv2;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    //AddItemScreen addItemScreen = mock(AddItemScreen.class);
    private AddItemScreen addItemScreen;
    //ItemScreen itemScreen = mock(ItemScreen.class);
    private ItemScreen itemScreen;
    @Test
    public void addition_isCorrect() {
        //assertEquals(4, 2 + 2);
        //when(addItemScreen.saveItem("hi"));
        addItemScreen = mock(AddItemScreen.class);
        itemScreen = mock(ItemScreen.class);
        //addItemScreen.saveItem("hi");

        //String item = itemScreen.getItems();
        //assertEquals("hi",item);
    }
/*
    @Test
    public void getObject_whenEmpty() {
        FileManager.removeObjects();
        assertNull(FileManager.getObject());
    }

    @Test
    public void saveObject_saveCorrectNumberOfObjects() {

        FileManager.removeObjects();
        FileManager.saveObject(new Item("coat"));
        assertEquals(1, FileManager.getObject().size());
    }

    @Test
    public void saveObject_saveCorrectDescription() {

        FileManager.removeObjects();
        FileManager.saveObject(new Item("coat"));
        assertEquals("coat", FileManager.getObject().get(0).getDescription());
    }
    @Test
    public void saveObject_areMultipleObjectsSaved(){
        FileManager.removeObjects();
        FileManager.saveObject(new Item("coat"));
        FileManager.saveObject(new Item("coat"));
        FileManager.saveObject(new Item("coat"));
        assertEquals(3, FileManager.getObject().size());
    }
*/
}