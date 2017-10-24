package com.example.kolin.fintechhomework;

import android.os.Handler;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by kolin on 24.10.2017.
 */

public class MyDownloader extends Thread {

    private Handler responseHandler;

    private WeakReference<Callback> callback;

    private int param;

    public interface Callback {
        void onListDownLoaded(List<String> data);
        void showLoading(boolean showLoading);
    }

    public MyDownloader(Handler responseHandler, MyDownloader.Callback callback) {
        this.responseHandler = responseHandler;
        this.callback = new WeakReference<>(callback);
    }

    public void setParam(int param){
        this.param = param;
    }

    @Override
    public void run() {
        if (isInterrupted()) return;

        responseHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) callback.get().showLoading(true);
            }
        });

        for (int i = 0; i < param; i++) {
            final List<String> testData = createTestData(i);

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();

                responseHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callback.get() != null) callback.get().showLoading(false);
                    }
                });

                break;
            }


            responseHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (callback.get() != null) callback.get().onListDownLoaded(testData);
                }
            });
        }

        responseHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callback.get() != null) callback.get().showLoading(false);
            }
        });
    }

    public List<String> createTestData(int prefix) {
        List<String> temp = new ArrayList<>();

        for (int i = 0; i < 5; i++)
            temp.add("TestData - " + prefix);

        return temp;
    }

    public void setCallback(Callback callback) {
        this.callback = new WeakReference<>(callback);
    }

    //Remove callback
    public void removeCallBack() {
        if (callback.get() != null) {
            callback.clear();
            callback = null;
        }
    }
}
