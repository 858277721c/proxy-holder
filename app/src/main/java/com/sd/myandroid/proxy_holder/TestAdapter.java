package com.sd.myandroid.proxy_holder;

import android.util.Log;

import com.sd.lib.holder.proxy.FProxyHolder;
import com.sd.lib.holder.proxy.ProxyHolder;

public class TestAdapter
{
    private final ViewHolderA mViewHolderA = new ViewHolderA();
    private final ViewHolderB mViewHolderB = new ViewHolderB();

    public final ProxyHolder<Callback> mCallbackHolder = new FProxyHolder<>(Callback.class);

    public TestAdapter()
    {
        // ViewHolderA
        mViewHolderA.mCallbackHolder.set(new ViewHolderA.Callback()
        {
            @Override
            public void onEventViewHolderA()
            {
                Log.i(MainActivity.TAG, "onEventViewHolderA in adapter");
            }
        });
        mViewHolderA.mCallbackHolder.addChild(mCallbackHolder);

        // ViewHolderB
        mViewHolderB.mCallbackHolder.set(new ViewHolderB.Callback()
        {
            @Override
            public void onEventViewHolderB()
            {
                Log.i(MainActivity.TAG, "onEventViewHolderB in adapter");
            }
        });
        mViewHolderB.mCallbackHolder.addChild(mCallbackHolder);
    }

    public void notifyEvent()
    {
        mViewHolderA.notifyEvent();
        mViewHolderB.notifyEvent();
        mCallbackHolder.get().onEventAdapter();
    }

    public interface Callback extends ViewHolderA.Callback, ViewHolderB.Callback
    {
        void onEventAdapter();
    }
}
