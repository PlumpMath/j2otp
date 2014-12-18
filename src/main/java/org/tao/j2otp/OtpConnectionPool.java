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

    public void returnBrokenResource(final OtpConnection resource) {
        if (resource != null) {
            returnBrokenResourceObject(resource);
        }
    }

    public void returnResource(final OtpConnection resource) {
        if (resource != null) {
//            resource.resetState();
            returnResourceObject(resource);
        }
    }


}
