package com.sd.myandroid.proxy_holder;

import com.sd.lib.holder.proxy.FProxyHolder;
import com.sd.lib.holder.proxy.ProxyHolder;

public class ViewHolderA
{
    public final ProxyHolder<Callback> mCallbackHolder = new FProxyHolder<>(Callback.class);

    public void notifyEvent()
    {
        mCallbackHolder.get().onEventViewHolderA();
    }

    public interface Callback
    {
        void onEventViewHolderA();
    }
}
