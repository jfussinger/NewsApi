package com.example.android.newsapi.utils;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

//https://github.com/udacity/android-architecture-components_toy_app/blob/master/app/src/main/java/com/example/android/todolist/AppExecutors.java

public class AppExecutors {

    private static final Object LOCK = new Object();
    private static AppExecutors sInstance;
    private final Executor diskIO;
    private final Executor mainThread;


    /**
     * Constructor for instantiating singleton AppExecutor class
     *
     * @param diskIO     Disk IO operations
     * @param mainThread Main Thread operations
     */
    private AppExecutors(Executor diskIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.mainThread = mainThread;

    }

    /**
     * A singleton class instantiation
     *
     * @return {@link AppExecutors}
     */
    public static AppExecutors getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new AppExecutors(
                        Executors.newSingleThreadExecutor(),
                        new MainThreadExecutor()
                );
            }
        }
        return sInstance;
    }

    public Executor getDiskIO() {
        return diskIO;
    }

    public Executor getMainThread() {
        return mainThread;
    }

    private static class MainThreadExecutor implements Executor {
        private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
