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


}