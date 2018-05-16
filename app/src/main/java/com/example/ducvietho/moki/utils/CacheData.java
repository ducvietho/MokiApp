package com.example.ducvietho.moki.utils;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by ducvietho on 19/04/2018.
 */

public class CacheData {
    private CacheData() {
    }
    public static void writeObject(Context context, Object object) throws IOException {
        String fileCache = "cache";
        FileOutputStream fos = context.openFileOutput( fileCache,Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(object);
        oos.close();
        fos.close();
    }

    public static Object readObject(Context context) throws IOException,
            ClassNotFoundException {
        String fileCache = "cache";
        FileInputStream fis = context.openFileInput(fileCache);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object object = ois.readObject();
        return object;
    }
}
