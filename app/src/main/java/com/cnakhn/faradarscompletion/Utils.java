package com.cnakhn.faradarscompletion;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.cnakhn.faradarscompletion.Activities.MainActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class Utils {
    private static final String TAG = "Utils";
    private Activity activity;
    private Context context;
    private String databaseName = "City.db";
    private SharedPreferences preferences;
    public static final String PREF_USER_INFO = "userInfo";

    public Utils(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_USER_INFO, Context.MODE_PRIVATE);
    }

    public Utils(Activity activity) {
        this.activity = activity;
    }

    public Utils(Context context, String databaseName) {
        this.context = context;
        this.databaseName = databaseName;
    }

    public void checkDatabase() {
        File dbFile = context.getDatabasePath(databaseName);
        if (!dbFile.exists()) {
            copyDbFile(dbFile);
            Log.i(TAG, "checkDatabase: Database copied.");
        }
    }

    private void copyDbFile(File dbFile) {
        try {
            int length = 0;
            byte[] buffer = new byte[1024];
            InputStream inputStream = context.getAssets().open(databaseName);
            dbFile.getParentFile().mkdirs();
            OutputStream outputStream = new FileOutputStream(dbFile);
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            inputStream.close();
            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setStatusBarColor(Activity activity, int color) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

    public static String inputStreamToString(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();
        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveUserLoginInfo(String username, String email) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", username);
        editor.putString("email", email);
        editor.apply();
    }

    public String getUsername() {
        if (preferences != null) {
            return preferences.getString("username", "");
        }
        return "";
    }

    public String getEmail() {
        if (preferences != null) {
            return preferences.getString("email", "");
        }
        return "";
    }

}
