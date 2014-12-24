package org.tao.j2otp;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by junjie on 12/16/14.
 */
public abstract class Pool<T> {

    private static final Logger _out = LogManager.getLogger(Pool.class);
    private static final Class<?> _clazz = Pool.class;
    protected GenericObjectPool<T> _pool;

    public Pool() {
    }

    public Pool(final GenericObjectPoolConfig poolConfig, final PooledObjectFactory<T> factory) {
        if (this._pool != null) {
            try {
                close();
                _out.warn(H.format(_clazz, "GenericObjectPoolConfig closed"));
            } catch (Exception e) {
                _out.error(e);
            }
        }

        this._pool = new GenericObjectPool<T>(factory, poolConfig);
        _out.debug(H.format(_clazz, "GenericObjectPoolConfig"));
    }

    public T getResource() {
        try {
            final T t = _pool.borrowObject();
            _out.debug(H.format(_clazz, "getResource"));
            return (t);
        } catch (final Exception e) {
            _out.error(e);
        }
        return (null);
    }

    public void returnResourceObject(final T resource) {
        if (resource == null) {
            return;
        }
        try {
            _pool.returnObject(resource);
            _out.debug(H.format(_clazz, "returnResourceObject"));
        } catch (final Exception e) {
            _out.error(e);
        }
    }

    public void returnBrokenResource(final T resource) {
        if (resource != null) {
            returnBrokenResourceObject(resource);
            _out.debug(H.format(_clazz, "returnBrokenResource"));
        }
    }

    public void returnResource(final T resource) {
        if (resource != null) {
            returnResourceObject(resource);
            _out.debug(H.format(_clazz, "returnResource"));
        }
    }

    public void destroy() {
        close();
        _out.debug(H.format(_clazz, "destroy"));
    }

    protected void returnBrokenResourceObject(final T resource) {
        try {
            _pool.invalidateObject(resource);
            _out.debug(H.format(_clazz, "returnBrokenResourceObject"));
        } catch (final Exception e) {
            _out.error(e);
        }
    }

    protected void close() {
        try {
            _pool.close();
            _out.debug(H.format(_clazz, "close"));
        } catch (final Exception e) {
            _out.error(e);
        }
    }
}
