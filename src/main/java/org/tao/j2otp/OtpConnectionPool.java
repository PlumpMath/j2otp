package org.tao.j2otp;

import com.ericsson.otp.erlang.OtpConnection;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * author: Junjie Mars
 */

public final class OtpConnectionPool extends Pool<OtpConnection> {

    private static final Logger _out = LogManager.getLogger(OtpConnectionPool.class);
    private static final Class<?> _clazz = OtpConnectionPool.class;

    public OtpConnectionPool(final GenericObjectPoolConfig config, final Options options) {
        super(config, new OtpConnectionFactory(options));
    }

    @Override
    public OtpConnection getResource() {
        final OtpConnection c = super.getResource();
//        c.setDataSource(this);
        _out.debug(H.format(_clazz, "getResource"));
        return (c);
    }

    public void returnResource(final OtpConnection resource) {
        if (null == resource) {
            _out.warn(H.format(_clazz, "returnResource null/empty"));
            return;
        }

        if (resource.isInterrupted()) {
            returnBrokenResource(resource);
        } else {
            returnBrokenResourceObject(resource);
        }

        _out.debug(H.format(_clazz, "returnResource"));
    }

    public final void returnBrokenResource(final OtpConnection resource) {
        if (resource != null) {
            returnBrokenResourceObject(resource);
            _out.debug(H.format(_clazz, "returnBrokenResource"));
        }
    }
}
