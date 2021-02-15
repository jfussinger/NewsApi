package com.example.android.newsapi.activity;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * Thread executor class
 */

public class ArticleExec implements Executor {

    @Override
    public void execute(@NonNull Runnable command) {
        new Thread(command).start();
    }
}
