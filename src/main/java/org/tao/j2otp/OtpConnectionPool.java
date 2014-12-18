package org.tao.j2otp;

import com.ericsson.otp.erlang.OtpConnection;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * author: Junjie Mars
 */

public final class OtpConnectionPool extends Pool<OtpConnection> {

    public OtpConnectionPool(final GenericObjectPoolConfig config, final Options options) {
        super(config, new OtpConnectionFactory(options));
    }

    @Override
    public OtpConnection getResource() {
        OtpConnection c = super.getResource();
//        c.setDataSource(this);
        return (c);
    }

    public void returnResource(final OtpConnection resource) {
        if (null == resource) {
            return;
        }

        if (resource.isInterrupted()) {
            returnBrokenResource(resource);
        } else {
            returnBrokenResourceObject(resource);
        }
    }

    public final void returnBrokenResource(final OtpConnection resource) {
        if (resource != null) {
            returnBrokenResourceObject(resource);
        }
    }


}
