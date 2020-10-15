package com.sd.myandroid.proxy_holder;

import com.sd.lib.holder.proxy.FProxyHolder;
import com.sd.lib.holder.proxy.ProxyHolder;

public class ViewHolder
{
    /**
     * ViewHolder回调对象持有
     */
    public final ProxyHolder<Callback> mCallbackHolder = new FProxyHolder<>(Callback.class);

    public void notifyEvent()
    {
        mCallbackHolder.get().onEventViewHolder();
    }

    public interface Callback
    {
        void onEventViewHolder();
    }
}
