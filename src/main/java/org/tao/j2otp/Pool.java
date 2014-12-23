package org.tao.j2otp;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * Created by junjie on 12/16/14.
 */
public abstract class Pool<T> {

    protected GenericObjectPool<T> _pool;

    public Pool() {
    }

    public Pool(final GenericObjectPoolConfig poolConfig, final PooledObjectFactory<T> factory) {
        if (this._pool != null) {
            try {
                close();
            } catch (Exception e) {
            }
        }

        this._pool = new GenericObjectPool<T>(factory, poolConfig);
    }

    public T getResource() {
        try {
            final T t = _pool.borrowObject();
            return (t);
        } catch (final Exception e) {
            System.out.println(e);
        }
        return (null);
    }

    public void returnResourceObject(final T resource) {
        if (resource == null) {
            return;
        }
        try {
            _pool.returnObject(resource);
        } catch (final Exception e) {
            System.out.println(e);
        }
    }

    public void returnBrokenResource(final T resource) {
        if (resource != null) {
            returnBrokenResourceObject(resource);
        }
    }

    public void returnResource(final T resource) {
        if (resource != null) {
            returnResourceObject(resource);
        }
    }

    public void destroy() {
        close();
    }

    protected void returnBrokenResourceObject(final T resource) {
        try {
            _pool.invalidateObject(resource);
        } catch (final Exception e) {
            System.out.println(e);
        }
    }

    protected void close() {
        try {
            _pool.close();
        } catch (final Exception e) {
            System.out.println(e);
        }
    }
}
