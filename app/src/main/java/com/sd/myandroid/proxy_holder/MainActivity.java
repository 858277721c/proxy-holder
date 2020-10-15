package com.sd.myandroid.proxy_holder;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    public static final String TAG = MainActivity.class.getSimpleName();

    private final TestAdapter mAdapter = new TestAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mAdapter.notifyEvent();
            }
        });

        mAdapter.mCallbackHolder.set(new TestAdapter.Callback()
        {
            @Override
            public void onEventViewHolder()
            {
                Log.i(TAG, "onEventViewHolder in Activity");
            }
        });
    }
}
