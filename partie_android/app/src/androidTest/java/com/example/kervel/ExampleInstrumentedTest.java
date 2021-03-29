package com.example.kervel;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.kervel.controleur.RetrofitClient;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import okhttp3.ResponseBody;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.kervel", appContext.getPackageName());
    }

    @Test
    public void testLogin() throws IOException {
        String email = "katia@gmail.com";
        String password = "katia";

        ResponseBody response =  new RetrofitClient().getService()
                .login(email, password)
                .execute()
                .body();

        Assert.assertNotNull(response.toString());
    }

/*    @Test
    public void testAddEvent() throws IOException {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String type = "type";
        String parameter = "parame";
        int id_parcelle = 200;
        String s = formatter.format(date);
        //EventInsert eventInsert = new EventInsert(date, type,parameter, id_parcelle);

        ResponseBody response =  RetrofitClient
                .getInstance()
                .getApi()
                .createEvent(s, type, parameter, id_parcelle)
                .execute()
                .body();

        Assert.assertNotNull(response.string());
    }*/
}