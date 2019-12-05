package com.sd.myandroid.proxy_holder;

import com.sd.lib.holder.proxy.FProxyHolder;
import com.sd.lib.holder.proxy.ProxyHolder;

public class ViewHolderB
{
    public final ProxyHolder<Callback> mCallbackHolder = new FProxyHolder<>(Callback.class);

    public void notifyEvent()
    {
        mCallbackHolder.get().onEventViewHolderB();
    }

    public interface Callback
    {
        void onEventViewHolderB();
    }
}
