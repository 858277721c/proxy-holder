package com.sd.lib.holder.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 代理对象持有者
 *
 * @param <T>
 */
public class FProxyHolder<T> implements ProxyHolder<T>
{
    private T mObject;

    private Class<T> mClass;
    private T mProxy;

    private Map<ProxyHolder<? extends T>, Object> mMapChildren;

    public FProxyHolder(Class<T> clazz)
    {
        if (!clazz.isInterface())
            throw new IllegalArgumentException("clazz must be an interface");

        mClass = clazz;
    }

    @Override
    public synchronized void set(T object)
    {
        mObject = object;
    }

    @Override
    public synchronized T get()
    {
        return getProxy();
    }

    @Override
    public synchronized void addChild(ProxyHolder<? extends T> child)
    {
        if (child == null)
            return;

        if (this == child)
            throw new IllegalArgumentException("child should not be current instance");

        if (mMapChildren == null)
            mMapChildren = new WeakHashMap<>();

        mMapChildren.put(child, null);
    }

    @Override
    public synchronized void removeChild(ProxyHolder<? extends T> child)
    {
        if (mMapChildren != null)
        {
            mMapChildren.remove(child);
            if (mMapChildren.isEmpty())
                mMapChildren = null;
        }
    }

    /**
     * 获得内部持有的代理对象
     *
     * @return
     */
    private T getProxy()
    {
        if (mProxy == null)
            mProxy = newProxyInstance(mClass, mInvocationHandler);
        return mProxy;
    }

    private final InvocationHandler mInvocationHandler = new InvocationHandler()
    {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
        {
            synchronized (FProxyHolder.this)
            {
                if (mObject != null)
                {
                    final Object realResult = method.invoke(mObject, args);
                    notifyChildren(method, args);
                    return realResult;
                } else
                {
                    return notifyChildren(method, args);
                }
            }
        }
    };

    private Object notifyChildren(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException
    {
        Object result = null;

        if (mMapChildren != null)
        {
            final List<ProxyHolder<? extends T>> list = new ArrayList<>(mMapChildren.keySet());
            for (ProxyHolder<? extends T> item : list)
            {
                final Object itemObject = item.get();
                result = method.invoke(itemObject, args);
            }
        }

        final Class<?> returnType = method.getReturnType();
        final boolean isVoid = returnType == void.class || returnType == Void.class;
        if (isVoid)
        {
            result = null;
        } else if (returnType.isPrimitive() && result == null)
        {
            if (boolean.class == returnType)
                result = false;
            else
                result = 0;
        }

        return result;
    }

    /**
     * 创建代理
     *
     * @param clazz   要创建代理的接口
     * @param handler
     * @return
     */
    private static <P> P newProxyInstance(Class<P> clazz, InvocationHandler handler)
    {
        return (P) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, handler);
    }
}