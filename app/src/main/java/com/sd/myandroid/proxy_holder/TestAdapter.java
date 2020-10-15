package com.sd.myandroid.proxy_holder;

import android.util.Log;

import com.sd.lib.holder.proxy.FProxyHolder;
import com.sd.lib.holder.proxy.ProxyHolder;

public class TestAdapter
{
    private final ViewHolder mViewHolder = new ViewHolder();

    /**
     * Adapter回调对象持有
     */
    public final ProxyHolder<Callback> mCallbackHolder = new FProxyHolder<>(Callback.class);

    public TestAdapter()
    {
        // 给ViewHolder设置回调对象
        mViewHolder.mCallbackHolder.set(new ViewHolder.Callback()
        {
            @Override
            public void onEventViewHolder()
            {
                Log.i(MainActivity.TAG, "onEventViewHolder set in adapter");
            }
        });
        // 给ViewHolder添加回调对象
        mViewHolder.mCallbackHolder.add(new ViewHolder.Callback()
        {
            @Override
            public void onEventViewHolder()
            {
                Log.i(MainActivity.TAG, "onEventViewHolder add in adapter");
            }
        });

        // 让ViewHolder的回调对象，通知Adapter的回调对象
        mViewHolder.mCallbackHolder.addChild(mCallbackHolder);
    }

    public void notifyEvent()
    {
        mViewHolder.notifyEvent();
    }

    public interface Callback extends ViewHolder.Callback
    {
    }
}
