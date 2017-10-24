package com.example.kolin.fintechhomework;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataFragment extends Fragment implements MyDownloader.Callback {

    public static final String TAG = DataFragment.class.getSimpleName();

    //Current data
    private List<String> data = new ArrayList<>();

    private DataFragmentListener listener;

    private MyDownloader downloader;

    //fragment callback
    public interface DataFragmentListener {
        void onDataResult(List<String> data);

        void showLoading(boolean showLoading);
    }

    public DataFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public void onListDownLoaded(List<String> data) {
        if (listener != null)
            listener.onDataResult(data);
        else
            this.data.addAll(data);
    }

    @Override
    public void showLoading(boolean showLoading) {
        if (listener != null)
            listener.showLoading(showLoading);
    }

    public void clearData() {
        this.data.clear();
    }

    /**
     * @param param number of downloadable lists with delay between
     */
    public void loadData(int param){
        downloader = new MyDownloader(new Handler(), DataFragment.this);
        downloader.setParam(param);
        downloader.start();
    }

    public void setUserLeave() {
        if (!downloader.isInterrupted())
            downloader.interrupt();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ");


        if (!downloader.isInterrupted())
            downloader.interrupt();


        downloader.removeCallBack();
        downloader = null;

        super.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof DataFragmentListener) {
            listener = (DataFragmentListener) context;

            if (!data.isEmpty()) {
                listener.onDataResult(data);
                data.clear();
            }
        } else
            throw new RuntimeException(context.toString() + " must implement DataFragmentListener");
    }

    @Override
    public void onDetach() {
        listener = null;

        Log.i(TAG, "onDetach: ");
        super.onDetach();
    }
}
