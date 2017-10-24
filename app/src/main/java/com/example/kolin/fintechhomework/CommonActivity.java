package com.example.kolin.fintechhomework;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CommonActivity extends AppCompatActivity implements DataFragment.DataFragmentListener {

    private static final String KEY_NUM = "key_num";
    private static final String TAG = CommonActivity.class.getSimpleName();

    private final String KEY_STATE_VISIBLE_PROGRESS = "key_progress_visible";
    private final String KEY_STATE_ENABLE_BTN = "key_btn_enable";
    private final String KEY_STATE_ADAPTER_DATA = "key_adapter_data";


    private Button buttonStart;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    private StringRecyclerAdapter adapter;
    //current number of task
    private int currentNum;

    private DataFragment dataFragment;

    /**
     * @param num Start task 1 or 2 from description in Fintech task
     */
    public static void start(Context context, int num) {
        Intent starter = new Intent(context, CommonActivity.class);
        starter.putExtra(KEY_NUM, num);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);

        adapter = new StringRecyclerAdapter();

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentNum = getIntent().getIntExtra(KEY_NUM, 0);

        TextView textView = findViewById(R.id.common_activity_text_view);
        textView.setText(getResources().getStringArray(R.array.array_text)[currentNum]);
        recyclerView = findViewById(R.id.common_activity_recycler_view);
        progressBar = findViewById(R.id.common_activity_progress_view);
        buttonStart = findViewById(R.id.common_activity_button_start);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            dataFragment = (DataFragment) fragmentManager.findFragmentByTag(DataFragment.TAG);

            if (dataFragment == null)
                fragmentManager.beginTransaction().add(new DataFragment(), DataFragment.TAG).commit();

            adapter.addData(savedInstanceState.getStringArrayList(KEY_STATE_ADAPTER_DATA));
            buttonStart.setEnabled(savedInstanceState.getBoolean(KEY_STATE_ENABLE_BTN));
            progressBar.setVisibility(savedInstanceState.getInt(KEY_STATE_VISIBLE_PROGRESS));
        } else {
            dataFragment = new DataFragment();
            fragmentManager.beginTransaction().add(dataFragment, DataFragment.TAG).commit();
        }


        setupRecyclerView();
        addButtonListener();
    }


    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(CommonActivity.this));
        recyclerView.addItemDecoration(new DividerItemDecoration(CommonActivity.this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    private void addButtonListener() {
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.clearData();
                dataFragment.clearData();

                //in according with task load particular data
                if (currentNum == 0)
                    //load one list of data with sleep 5000 milisec
                    dataFragment.loadData(1);
                else
                    //load two lists of data with sleep 5000 milisec between lists
                    dataFragment.loadData(2);

            }
        });
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();

        dataFragment.setUserLeave();
        Log.i(TAG, "onUserLeaveHint: ");
    }

    public void showLoadingProcess(boolean show) {
        if (show) {
            buttonStart.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            buttonStart.setEnabled(true);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onDataResult(List<String> data) {
        adapter.addData(data);
    }

    @Override
    public void showLoading(boolean showLoading) {
        showLoadingProcess(showLoading);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(KEY_STATE_ENABLE_BTN, buttonStart.isEnabled());
        outState.putInt(KEY_STATE_VISIBLE_PROGRESS, progressBar.getVisibility());
        outState.putStringArrayList(KEY_STATE_ADAPTER_DATA, new ArrayList<>(adapter.getData()));
    }

    @Override
    protected void onDestroy() {
        adapter.clearData();
        adapter = null;

        super.onDestroy();
    }
}
