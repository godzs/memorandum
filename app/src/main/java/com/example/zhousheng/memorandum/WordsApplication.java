package com.example.zhousheng.memorandum;

import android.app.Application;
import android.content.Context;
/* designed by zhousheng */
public class WordsApplication extends Application {
    private static Context context;
    public static Context getContext(){
        return WordsApplication.context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        WordsApplication.context=getApplicationContext();
    }
}