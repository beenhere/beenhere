package com.sakana.beenhere.ui;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by caesare on 9/20/13.
 */
public class BLStorage {

    public static boolean save(Context context, String session , String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(session, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String read(Context context, String session , String key) {
        SharedPreferences se =
                context.getSharedPreferences(
                        session ,
                        Context.MODE_PRIVATE);
        return se.getString(key, null);
    }

}
