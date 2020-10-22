package com.sd.lib.holder.proxy;

public interface ProxyHolder<T>
{
    /**
     * 保存对象
     *
     * @param object
     */
    void set(T object);

    /**
     * 返回代理对象
     *
     * @return
     */
    T get();

    /**
     * 添加对象
     *
     * @param object
     */
    void add(T object);

    /**
     * 移除对象
     *
     * @param object
     */
    void remove(T object);

    /**
     * 添加child
     *
     * @param child
     */
    void addChild(ProxyHolder<? extends T> child);

    /**
     * 移除child
     *
     * @param child
     */
    void removeChild(ProxyHolder<? extends T> child);
}
